package com.xuecheng.manage_cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Daniel Liu 2019/12/3 14:03
 */
@Controller//不能用restController，它会把返回的数据变成json
@RequestMapping( "/freemarker" )
public class FreemarkerTestController {


    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/banner")
    public String freemarkerTest(Map<String,Object> map){
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getModel/5c820e478bb1701f84f2d5c5", Map.class);
        map.putAll(forEntity.getBody());
        return "index_banner";
    }

}