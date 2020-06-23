package com.xuecheng.api;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Daniel Liu 2019/11/18 9:04
 */
public interface CmsPageControllerApi {

    @ApiOperation( "自定义条件分页查询" )
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    @ApiOperation( "新增页面" )
    CmsPageResult add(CmsPage cmsPage);

    @ApiOperation( "根据Id查询页面信息" )
    CmsPage findById(String id);

    @ApiOperation( "更新页面信息" )
    ResponseResult update(String id, CmsPage cmsPage);

    @ApiOperation( "页面删除" )
    ResponseResult del(@PathVariable String id);

    @ApiOperation( "发布页面" )
    ResponseResult postPage(String id);

    @ApiOperation( "保存页面" )
    CmsPageResult save(CmsPage cmsPage);
}