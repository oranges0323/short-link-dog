CREATE TABLE `short_link`
(
    `id`           BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `long_url`     VARCHAR(2048) NOT NULL COMMENT '原始长链接',
    `long_url_md5` CHAR(32)      NOT NULL COMMENT '长链接MD5值（用于快速查找）',
    `short_code`   VARCHAR(20)   NULL COMMENT '短码（如 abc123）',
    `create_time`  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `expire_time`  DATETIME               DEFAULT NULL COMMENT '过期时间（NULL表示永不过期）',
    `visit_count`  BIGINT        NOT NULL DEFAULT 0 COMMENT '访问次数',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_short_code` (`short_code`),
    UNIQUE KEY `uk_long_url_md5` (`long_url_md5`),
    KEY `idx_expire_time` (`expire_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT = '短链接映射表';

ALTER TABLE short_link
    MODIFY COLUMN short_code VARCHAR(20) NULL;
