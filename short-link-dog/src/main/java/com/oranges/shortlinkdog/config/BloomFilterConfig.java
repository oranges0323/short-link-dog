package com.oranges.shortlinkdog.config;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

@Configuration
public class BloomFilterConfig {
    private static int size = 1000000;//预计多少数据

    private static double fpp = 0.01;//误判值

    @Bean
    public BloomFilter<String> bloomFilter() {
            return BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), size, fpp);
    }
}
