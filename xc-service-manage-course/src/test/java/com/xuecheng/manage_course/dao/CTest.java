package com.xuecheng.manage_course.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

//import org.junit.jupiter.api.Test;

/**
 * @author Daniel Liu 2020/4/27 15:09
 */

//@RunWith( SpringRunner.class )
@SpringBootTest
//@ActiveProfiles( "dev" )
//@Log4j2
public class CTest {
    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void findCourseListPage() {
        PageHelper.startPage(1, 2);
        CourseListRequest courseListRequest = new CourseListRequest();
//        courseListRequest.setCompanyId("1");
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
        Stream.of(courseListPage.getResult()).forEach(System.out::println);
    }


}