package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Daniel Liu 2019/11/19 18:14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    private CmsPageRepository cmsPageRepository;


    @Test
    public void testFindAll() {
        List<CmsPage> pages = cmsPageRepository.findAll();
        System.out.println(pages);
    }


    @Test
    public void findCmsPageByPageAliase() {
//        Optional<CmsPage> cmsPage = CmsPageRepository.findCmsPageByPageAliase("ccc");
//        if (cmsPage.isPresent()) {
//            System.out.println(cmsPage);
    }
}