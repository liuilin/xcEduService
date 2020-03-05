package com.xuecheng.api;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

/**
 * 课程api
 *
 * @author Daniel Liu 2019/12/18 9:25
 */
public interface CourseControllerApi {
    @ApiOperation( "教学计划查询" )
    TeachplanNode findTeachPlanList(String courseId);

    @ApiOperation( "添加教学计划" )
    ResponseResult addTeachPlan(Teachplan teachplan);
}
