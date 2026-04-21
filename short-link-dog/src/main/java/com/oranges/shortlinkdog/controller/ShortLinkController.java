package com.oranges.shortlinkdog.controller;

import com.oranges.shortlinkdog.common.BaseResponse;
import com.oranges.shortlinkdog.common.ResultUtils;
import com.oranges.shortlinkdog.exception.BusinessException;
import com.oranges.shortlinkdog.exception.ErrorCode;
import com.oranges.shortlinkdog.service.ShortLinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
public class ShortLinkController {

    @Resource
    private ShortLinkService shortLinkService;

    /**
     * 生成短链接
     * @param url
     * @return
     */
    @PostMapping("/create")
    public BaseResponse<String> create(String url) {
        //校验url是否为空
        if(url==null || url.isEmpty()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"url不能为空");
        }
        String shortCode = shortLinkService.createShortLinkByUrl(url);
        String shortLink = "http://localhost:8126/linkapi/visit/"+shortCode;
        return ResultUtils.success(shortLink);
    }
    /**
     * 访问短链接
     */
    @GetMapping("/v/{shortLink}")
    public void visit(@PathVariable("shortLink") String shortLink, HttpServletResponse response) throws IOException {

        String longLink = shortLinkService.getLongLinkByShortLink(shortLink);
        log.info("重定向到：{}",longLink);
        response.sendRedirect(longLink);
    }

}
