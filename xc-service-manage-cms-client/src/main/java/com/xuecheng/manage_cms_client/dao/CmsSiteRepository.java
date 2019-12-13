package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Daniel Liu 2019/12/12 20:28
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {

}