package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Daniel Liu 2019/12/3 13:57
 */

@RunWith( SpringRunner.class )
@SpringBootTest
public class FreemarkerTest {

    @Autowired
    RestTemplate restTemplate;

//    @Test
//    public String freemarkerTest(Map<String,Object> map){
//        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getModel/5c820e478bb1701f84f2d5c5", Map.class);
//        map.putAll(forEntity.getBody());
//        return "inner_index";
//    }

    @Test
    public void rest(){
        String fooResourceUrl
                = "http://localhost:31001/cms/config/getModel/5c820e478bb1701f84f2d5c";
        ResponseEntity<CmsConfig> response
                = restTemplate.getForEntity(fooResourceUrl , CmsConfig.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
}