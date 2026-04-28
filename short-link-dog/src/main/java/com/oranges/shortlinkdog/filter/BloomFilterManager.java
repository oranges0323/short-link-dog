package com.oranges.shortlinkdog.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.hash.BloomFilter;
import com.oranges.shortlinkdog.mapper.ShortLinkMapper;
import com.oranges.shortlinkdog.model.dto.ShortLink;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Component
public class BloomFilterManager {
    @Resource
    public BloomFilter<String> bloomFilter;
    @Resource
    private ShortLinkMapper shortLinkMapper;

    @PostConstruct
    public void warmUp() {
        List<ShortLink> list = shortLinkMapper.selectList(new QueryWrapper<>());
        for (ShortLink shortLink : list) {
            bloomFilter.put(shortLink.getShort_code());
        }

    }
}
