package com.xuecheng.manage_course.controller;

import com.xuecheng.api.CourseControllerApi;
import com.xuecheng.framework.domain.cms.response.CourseResult;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Daniel Liu 2019/12/18 8:38
 */

@RestController
@RequestMapping( "/course" )
public class CourseController implements CourseControllerApi {

    @Autowired
    private CourseService courseService;

    @Override
    @PostMapping( "/teachplan/add" )
    public ResponseResult addTeachPlan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachPlan(teachplan);
    }

    @Override
    @GetMapping( "/coursebase/list/{page}/{size}" )
    public QueryResponseResult<CourseInfo> findCourseList(@PathVariable int page, @PathVariable int size, CourseListRequest courseListRequest) {
        return courseService.findCourseList(page, size, courseListRequest);
    }

    @Override
    @PostMapping("/coursebase/add")
    public CourseResult save(@RequestBody CourseBase courseBase) {
        return courseService.save(courseBase);
    }

    @GetMapping( "/teachplan/list/{courseId}" )
    public TeachplanNode findTeachPlanList(@PathVariable @Valid String courseId) {
        return courseService.findTeachplanList(courseId);
    }

    @PutMapping( "/{id}" )
    public ResponseResult update(@PathVariable String id, @RequestBody @Valid Void input) {
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @DeleteMapping( "/{id}" )
    public ResponseResult delete(@PathVariable String id, @RequestBody @Valid Void input) {
        return new ResponseResult(CommonCode.SUCCESS);
    }

}