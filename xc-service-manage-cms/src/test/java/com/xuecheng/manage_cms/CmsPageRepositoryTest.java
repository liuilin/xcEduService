package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
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
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);


        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageAliase("轮播");

        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);


        System.out.println(all.getContent());
    }


    @Test
    public void findCmsPageByPageAliase() {
//        Optional<CmsPage> cmsPage = CmsPageRepository.findCmsPageByPageAliase("ccc");
//        if (cmsPage.isPresent()) {
//            System.out.println(cmsPage);
    }
}