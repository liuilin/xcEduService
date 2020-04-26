package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.TeachPlanRepository;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * 课程服务层
 *
 * @author Daniel Liu 2019/12/18 8:22
 */
@Service
public class CourseService {
    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private TeachPlanRepository teachPlanRepository;
    @Autowired
    private CourseBaseRepository courseBaseRepository;

    public TeachplanNode findTeachplanList(String courseId) {
        return teachplanMapper.findTeachPlanById(courseId);
    }

    public void print(String s) {
        System.out.print(s);
    }

    @Transactional
    public ResponseResult addTeachPlan(Teachplan teachplan) {
        if (teachplan == null || StringUtils.isEmpty(teachplan.getCourseid()) || StringUtils.isEmpty(teachplan.getPname())) {
            throw new CustomException(CommonCode.INVALID_PARAMETER);
        }
        String courseid = teachplan.getCourseid();
        //页面传入的父id，可能没有
        String parentid = teachplan.getParentid();
        if (StringUtils.isEmpty(parentid)) {
            parentid = this.getTeachPlanRootNode(courseid);
        }
        Optional<Teachplan> teachplanOptional = teachPlanRepository.findById(parentid);
        Teachplan parentNode = teachplanOptional.get();
        //父节点级别
        String grade = parentNode.getGrade();
        //保存课程
        Teachplan teachplanNew = new Teachplan();
        BeanUtils.copyProperties(teachplan, teachplanNew);
        teachplanNew.setParentid(parentid);
        teachplanNew.setCourseid(courseid);
        //grade级别，根据父节点的级别来设置
        if (grade.equals("1")) {
            teachplanNew.setGrade("2");
        } else {
            teachplanNew.setGrade("3");
        }
        teachPlanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取根节点，如果没有则添加根节点
     *
     * @param courseid
     * @return
     */
    private String getTeachPlanRootNode(String courseid) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseid);
        if (!optional.isPresent()) {
            return null;
        }
        CourseBase courseBase = optional.get();
        List<Teachplan> teachplans = teachPlanRepository.findAllByCourseidAndParentid(courseid, "0");
//        Optional.ofNullable(teachplans).orElseThrow(() -> new ServiceException("不存在或是已被删除"));
        if (CollectionUtils.isEmpty(teachplans)) {
            //查询不到根节点，自动添加根节点
            Teachplan teachplan = new Teachplan();
            teachplan.setPname(courseBase.getName());
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setCourseid(courseid);
            teachPlanRepository.save(teachplan);
            return teachplan.getId();
        }
        //返回根节点id
        return teachplans.get(0).getId();
    }
}