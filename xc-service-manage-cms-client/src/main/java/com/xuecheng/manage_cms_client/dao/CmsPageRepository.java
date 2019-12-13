package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Daniel Liu 2019/12/12 19:53
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

}
