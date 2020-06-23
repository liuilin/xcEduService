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

//必须是controller才行，不然返回前端的不是字符串。restcontroller注解返回的是json
@Controller
@RequestMapping( "/cms" )
public class CmsPreviewController extends BaseController {

    @Autowired
    private CmsPageService cmsPageService;

    /**
     * 页面预览
     *
     * @param pageId
     */
    @GetMapping( "/preview/{pageId}" )
    public void getPageHtml(@PathVariable String pageId) {
        String pageHtml = cmsPageService.getPageHtml(pageId);
        if (StringUtils.isNotEmpty(pageHtml)) {
            try {
                ServletOutputStream outputStream = response.getOutputStream();
                //由于Nginx先请求cms的课程预览功能得到html页面，再解析页面中的ssi标签，这里必须保证cms页面预览返回的页面的Content-Type为text/html;charset=utf-8
                response.setHeader("Content-type", "text/html;charset=utf-8");
                //通过response对象将内容输出
                outputStream.write(pageHtml.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}