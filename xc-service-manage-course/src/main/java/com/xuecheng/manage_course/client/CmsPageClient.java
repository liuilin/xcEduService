package com.xuecheng.manage_course.client;

import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Daniel Liu 2020/6/17 17:29
 */

@FeignClient( value = XcServiceList.XC_SERVICE_MANAGE_CMS )
public interface CmsPageClient {
    @GetMapping( "/cms/page/findById/{id}" )
    CmsPage findById(@PathVariable( "id" ) String id);

    //    @PostMapping( "/save" )
//    CoursePublishResult save();
    @PostMapping( "/cms/page/save" )
    CmsPageResult save(CmsPage cmsPage);

    @PostMapping( "/cms/page/oneClickPostPage" )
    CmsPostPageResult oneClickPostPage(CmsPage cmsPage);
}