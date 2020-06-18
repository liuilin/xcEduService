package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_course.client.CmsPageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Daniel Liu 2019/11/19 18:14
 */
@SpringBootTest
@RunWith( SpringRunner.class )
public class FeignTest {

    @Autowired
    CmsPageClient cmsPageClient;

    @Test
    public void testFeign() {
        CmsPage cmsPage = cmsPageClient.findById("5a754adf6abb500ad05688d9");
        System.out.println("cmsPage = " + cmsPage );
    }
}