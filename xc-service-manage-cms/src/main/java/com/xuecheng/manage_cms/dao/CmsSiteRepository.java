package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Daniel Liu 2019/12/2 9:34
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {

}
