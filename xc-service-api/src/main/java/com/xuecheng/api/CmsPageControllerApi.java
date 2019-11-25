package com.xuecheng.api;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;

/**
 * @author Daniel Liu 2019/11/18 9:04
 */
public interface CmsPageControllerApi {

    /**自定义条件分页查询
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    /**保存页面
     * @param cmsPage
     * @return
     */
    CmsPageResult add(CmsPage cmsPage);
}