package com.oranges.shortlinkdog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.oranges.shortlinkdog.mapper")
public class ShortLinkDogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortLinkDogApplication.class, args);
    }

}
