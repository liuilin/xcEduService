package com.xuecheng.manage_course.dao;

//import org.junit.Test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

/**
 * @author Daniel Liu 2020/4/27 13:58
 */
@RunWith( SpringRunner.class )
@SpringBootTest
//@RunWith( SpringRunner.class )
public class CourseMapperTest {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    void findCourseBaseById() {
        System.out.println("courseMapper = " + courseMapper.findCourseBaseById("297e7c7c62b888f00162b8a7dec20000") );
    }

    @Test
    public void findCourseListPage() {
        PageHelper.startPage(1, 2);
        CourseListRequest courseListRequest = new CourseListRequest();
//        courseListRequest.setCompanyId("1");
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
        Stream.of(courseListPage.getResult()).forEach(System.out::println);
    }

}