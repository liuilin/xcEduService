package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Daniel Liu 2019/12/17 19:53
 */
@Mapper
public interface TeachplanMapper {
    TeachplanNode findTeachPlanById(String courseId);
}