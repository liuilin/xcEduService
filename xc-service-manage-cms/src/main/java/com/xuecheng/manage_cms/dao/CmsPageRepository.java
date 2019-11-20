package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Daniel Liu 2019/11/19 18:09
 */
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {

//    Optional<CmsPage> findCmsPageByPageAliase(String aliase);
}