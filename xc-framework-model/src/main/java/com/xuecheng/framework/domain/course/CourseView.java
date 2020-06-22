package com.xuecheng.framework.domain.course;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 课程视图对象
 *
 * @author Daniel Liu 2020/6/22 12:23
 */
@Data
@NoArgsConstructor
@ToString
public class CourseView implements Serializable {
    CourseBase courseBase;//基础信息
    CourseMarket courseMarket;//课程营销
    CoursePic coursePic;//课程图片
    com.xuecheng.framework.domain.course.ext.TeachplanNode TeachplanNode;//教学计划
}