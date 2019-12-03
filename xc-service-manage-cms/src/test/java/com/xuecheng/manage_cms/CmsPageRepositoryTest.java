package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @author Daniel Liu 2019/11/19 18:14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private CmsConfigRepository cmsConfigRepository;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;
    @Autowired
    private RestTemplate restTemplate;

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

    @Test
    public void findById(){
        cmsConfigRepository.findById("5c820e478bb1701f84f2d5c5").orElse(null);
//        cmsPageRepository.findById("5a795ac7dd573c04508f3a56").orElse(null);
        cmsSiteRepository.findById("5a751fab6abb5044e0d19ea1").orElse(null);
    }

    @Test
    public void restTemplateTest(){
        ResponseEntity<CmsConfig> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getModel/5c820e478bb1701f84f2d5c5", CmsConfig.class);
        System.out.println(forEntity);
    }

}