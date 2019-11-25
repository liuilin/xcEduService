package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Daniel Liu 2019/11/19 18:09
 */
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {

    /**
     * 添加根据页面名称、站点Id、页面webpath查询页面方法，此方法用于校验页面是否存在
     *
     * @param pageName
     * @param siteId
     * @param pageWebPath
     * @return
     */
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);
}