package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Daniel Liu 2020/4/28 11:12
 */
@Repository
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
    SysDictionary findBydType(String type);
}