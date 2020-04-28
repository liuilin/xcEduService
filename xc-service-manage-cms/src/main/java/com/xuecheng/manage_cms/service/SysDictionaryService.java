package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.dao.SysDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Daniel Liu 2020/4/28 11:07
 */
@Service
public class SysDictionaryService {
    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    public SysDictionary findByType(String type) {
        return sysDictionaryRepository.findBydType(type);
    }

}