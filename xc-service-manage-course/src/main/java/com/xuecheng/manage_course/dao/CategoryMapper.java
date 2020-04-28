package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Daniel Liu 2020/4/27 17:19
 */
@Mapper
public interface CategoryMapper {
    CategoryNode findList();
}
