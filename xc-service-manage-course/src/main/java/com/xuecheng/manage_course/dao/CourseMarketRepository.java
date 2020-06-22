package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseMarket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Daniel Liu 2020/6/22 12:33
 */
public interface CourseMarketRepository extends JpaRepository<CourseMarket,String> {
}
