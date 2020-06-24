package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 站点dao
 *
 * @author Daniel Liu 2020/6/23 17:11
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {

}