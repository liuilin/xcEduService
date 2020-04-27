package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Daniel Liu 2019/12/22 16:46
 */
public interface TeachPlanRepository extends JpaRepository<Teachplan, String> {
    List<Teachplan> findAllByCourseidAndParentid(String courseId, String parentId);
}