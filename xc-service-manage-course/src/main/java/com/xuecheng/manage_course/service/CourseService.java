package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 课程服务层
 *
 * @author Daniel Liu 2019/12/18 8:22
 */
@Service
public class CourseService {
    @Autowired
    private TeachplanMapper teachplanMapper;

    public TeachplanNode findTeachplanList(String courseId){
        return teachplanMapper.findTeachPlanById(courseId);
    }
}