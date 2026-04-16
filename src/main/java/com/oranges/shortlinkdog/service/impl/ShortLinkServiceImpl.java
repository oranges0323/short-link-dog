package com.oranges.shortlinkdog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oranges.shortlinkdog.model.dto.ShortLink;
import com.oranges.shortlinkdog.service.ShortLinkService;
import com.oranges.shortlinkdog.mapper.ShortLinkMapper;
import org.springframework.stereotype.Service;

/**
* @author chen zhi
* @description 针对表【short_link(短链接映射表)】的数据库操作Service实现
* @createDate 2026-04-16 17:00:10
*/
@Service
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLink>
    implements ShortLinkService{

}




