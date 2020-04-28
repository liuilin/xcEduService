package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.SysDictionaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Daniel Liu 2020/4/28 10:58
 */
@RestController
@RequestMapping("/sys/dictionary")
public class SysDictionaryController implements SysDictionaryControllerApi {
    @Autowired
    private SysDictionaryService sysDictionaryService;

    @GetMapping("/{type}")
    public SysDictionary findByType(@PathVariable String type) {
        return sysDictionaryService.findByType(type);
    }
}