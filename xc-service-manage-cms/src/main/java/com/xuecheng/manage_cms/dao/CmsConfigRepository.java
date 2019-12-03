package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Daniel Liu 2019/12/2 9:34
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {

}
