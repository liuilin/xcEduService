package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;

/**
 * @author Daniel Liu 2020/4/27 17:19
 */
public interface CategoryMapper {
    CategoryNode findList();
}
