package com.oranges.shortlinkdog.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 短链接映射表
 * @TableName short_link
 */
@TableName(value ="short_link")
@Data
public class ShortLink {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 原始长链接
     */
    private String long_url;

    /**
     * 长链接MD5值（用于快速查找）
     */
    private String long_url_md5;

    /**
     * 短码（如 abc123）
     */
    private String short_code;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 过期时间（NULL表示永不过期）
     */
    private Date expire_time;

    /**
     * 访问次数
     */
    private Long visit_count;
}