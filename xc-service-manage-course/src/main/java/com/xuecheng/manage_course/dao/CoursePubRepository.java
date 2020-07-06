package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CoursePub;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Daniel Liu 2020/7/6 16:58
 */
public interface CoursePubRepository extends MongoRepository<CoursePub, String> {

}