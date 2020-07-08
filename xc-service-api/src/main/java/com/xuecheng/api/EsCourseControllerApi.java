package com.xuecheng.api;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Daniel Liu 2020/7/8 10:37
 */
@Api(value = "课程搜索",description = "课程搜索",tags = {"课程搜索"})
public interface EsCourseControllerApi {
    @ApiOperation( "课程搜索" )
    QueryResponseResult<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam);
}
