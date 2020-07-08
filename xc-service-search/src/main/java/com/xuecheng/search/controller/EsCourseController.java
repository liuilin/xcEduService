package com.xuecheng.search.controller;

import com.xuecheng.api.EsCourseControllerApi;
import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.search.service.EsCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Daniel Liu 2020/7/8 10:41
 */
@RestController
@RequestMapping( "/search/course" )
public class EsCourseController implements EsCourseControllerApi {

    @Autowired
    public EsCourseService esCourseService;


    @Override
    @GetMapping( "/list/{page}/{size}" )
    public QueryResponseResult<CoursePub> list(@PathVariable int page, @PathVariable int size, CourseSearchParam courseSearchParam) {
        return esCourseService.list(page, size, courseSearchParam);
    }
}