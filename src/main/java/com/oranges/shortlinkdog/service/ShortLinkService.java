package com.oranges.shortlinkdog.service;

import com.oranges.shortlinkdog.model.dto.ShortLink;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chen zhi
* @description 针对表【short_link(短链接映射表)】的数据库操作Service
* @createDate 2026-04-16 17:00:10
*/
public interface ShortLinkService extends IService<ShortLink> {
    /**
     * 根据url创建短链接
     * @param Url
     * @return
     */
    String createShortLinkByUrl(String Url);

    /**
     * 根据短链接获取长链接
     * @return
     */
    String getLongLinkByShortLink(String shortUrl);

}
