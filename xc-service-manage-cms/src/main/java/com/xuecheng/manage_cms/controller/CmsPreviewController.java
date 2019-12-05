package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Daniel Liu 2019/12/5 19:10
 */

@Controller
@RequestMapping( "/cms/preview" )
public class CmsPreviewController extends BaseController {

    @Autowired
    private CmsPageService cmsPageService;

    @GetMapping( "/preview/{pageId}" )
    public void getPageHtml(@PathVariable String pageId) {
        String pageHtml = cmsPageService.getPageHtml(pageId);
        if (StringUtils.isNotEmpty(pageHtml)) {
            try {
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(pageHtml.getBytes(StandardCharsets.UTF_8));
                //必须是controller才行，不然回事字符串
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}