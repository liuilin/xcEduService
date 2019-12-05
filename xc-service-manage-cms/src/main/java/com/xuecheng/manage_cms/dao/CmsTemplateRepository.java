package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Daniel Liu 2019/12/5 8:32
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {
}