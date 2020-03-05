package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.TeachPlanRepository;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public TeachplanNode findTeachplanList(String courseId) {
        return teachplanMapper.findTeachPlanById(courseId);
    }

    public ResponseResult addTeachPlan(Teachplan teachplan) {
        if (teachplan == null || StringUtils.isEmpty(teachplan.getCourseid()) || StringUtils.isEmpty(teachplan.getPname())) {
            throw new CustomException(CommonCode.INVALID_PARAMETER);
        }
        String parentid = teachplan.getParentid();
        String courseid = teachplan.getCourseid();
        if (StringUtils.isEmpty(parentid)) {
            parentid = this.getTeachPlanRootNode(courseid);
        }
        //保存课程
        Teachplan teachplan1 = new Teachplan();
        BeanUtils.copyProperties(teachplan,teachplan1);
        teachplan1.setParentid(parentid);
        teachplan1.setCourseid(courseid);
        teachPlanRepository.save(teachplan1);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    private String getTeachPlanRootNode(String courseid) {
        List<Teachplan> teachplans = teachPlanRepository.findAllByCourseidAndParentid(courseid, "0");
        Optional.ofNullable(teachplans).orElseThrow(() -> new ServiceException("不存在或是已被删除"));
        if (CollectionUtils.isEmpty(teachplans)) {
            return null;
        }
        return teachplans.get(0).getParentid();
    }
}