package com.oranges.shortlinkdog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oranges.shortlinkdog.exception.BusinessException;
import com.oranges.shortlinkdog.exception.ErrorCode;
import com.oranges.shortlinkdog.exception.ThrowUtils;
import com.oranges.shortlinkdog.model.dto.ShortLink;
import com.oranges.shortlinkdog.service.ShortLinkService;
import com.oranges.shortlinkdog.mapper.ShortLinkMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
* @author chen zhi
* @description 针对表【short_link(短链接映射表)】的数据库操作Service实现
* @createDate 2026-04-16 17:00:10
*/
@Service
@Slf4j
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLink>
    implements ShortLinkService, IService<ShortLink> {


    private static final String BASE62_CHARS =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Resource
    private ShortLinkMapper shortLinkMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public String createShortLinkByUrl(String url) {
        //流程
        /**
         * 1.校验
         *  2.查询是否存在短码（直接查数据库）
         *  3.生成短码
         *  4.存入数据库
         *  5.返回
         */
        /**
         * 优化后
         * 不查是否存在，直接插入，失败了就代表数据库已经有了(因为数据库的long_url_md5 设置了唯一索引）
         * 就直接再查一次就行
         */

        //校验url
        try {
            URL newurl = new URL(url);
            //校验协议
            if(newurl.getProtocol() == null || newurl.getProtocol().isEmpty()){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"协议非法的url");

            }
            if(!newurl.getProtocol().equals("http") && !newurl.getProtocol().equals("https")){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"协议非法的url");
            }
            if(newurl.getHost() == null || newurl.getHost().isEmpty()){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"host非法的url");
            }
        } catch (MalformedURLException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"非法的url");
        }
        String long_url_MD5 = MD5.create().digestHex(url);


        //没有短码则生成
        //生成短码，需要获取唯一id，所以需要先存数据库再获取
        ShortLink shortLink = new ShortLink();
        shortLink.setLong_url(url);
        shortLink.setCreate_time(DateUtil.date());
        shortLink.setLong_url_md5(long_url_MD5);
        shortLink.setExpire_time(DateUtil.offsetDay(DateUtil.date(), 7));

        try {
            //成功则生成短码

            this.save(shortLink);
            String short_code = encodeBase62(shortLink.getId());
            shortLink.setShort_code(short_code);
            boolean update = this.updateById(shortLink);
            ThrowUtils.throwIf(!update, ErrorCode.SYSTEM_ERROR,"系统错误");

            return short_code;
        } catch (DuplicateKeyException e) {
            //已经存在直接返回
            ShortLink selectOne = shortLinkMapper.selectOne(new QueryWrapper<ShortLink>().eq("long_url_md5", long_url_MD5));
            if(selectOne != null && selectOne.getShort_code() != null){
                return selectOne.getShort_code();

            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"系统错误");
        }

    }


    @Override
    public String getLongLinkByShortLink(String shortCode) {
        /**
         * todo
         *  1.校验
         *  2.获取长链接 分两步，查询redis，没有在查数据库（如果在数据库查到就存一下redis）
         *  3.返回
         */
        if(shortCode == null||shortCode.isEmpty()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"短码为空");
        }


        ShortLink shortLink = new ShortLink();
        //查redis
        Object o = redisTemplate.opsForValue().get(shortCode);
        if(o != null){
            BeanUtil.copyProperties(o,shortLink);
            return shortLink.getLong_url();
        }
        //查数据库
         shortLink = shortLinkMapper.selectOne(new QueryWrapper<ShortLink>().eq("short_code", shortCode));
        if(shortLink == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"短链接不存在");
        }
//        //检查是否过期（先不管）
//        if(shortLink.getExpire_time().before(DateUtil.date())){
//            throw new BusinessException(ErrorCode.PARAMS_ERROR,"短链接已过期");
//        }
        //存redis
        redisTemplate.opsForValue().set(shortCode,shortLink, 7, TimeUnit.DAYS);


        return shortLink.getLong_url();
    }

    /**
     * 自定义转62进制
     * @param num
     * @return
     */
    private String encodeBase62(long num) {
        if (num == 0) return "0";

        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(BASE62_CHARS.charAt((int)(num % 62)));
            num /= 62;
        }
        return sb.reverse().toString();
    }

}




