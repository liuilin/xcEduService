package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CMS 内容管理模块
 * @author Daniel Liu 2019/12/2 10:11
 */

@RestController
@RequestMapping( "/cms/config" )
public class CmsConfigController implements CmsConfigControllerApi {

    @Autowired
    private CmsPageService cmsPageService;
    

    @Override
    @GetMapping("/getModel/{id}")
    public CmsConfig getModel(@PathVariable String id){
        return cmsPageService.getConfigById(id);
    }
}