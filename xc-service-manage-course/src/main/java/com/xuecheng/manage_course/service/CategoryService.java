package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.dao.CategoryMapper;

/**
 * @author Daniel Liu 2020/4/27 17:17
 */
public class CategoryService {

    private CategoryMapper categoryMapper;

    public CategoryNode findList() {
        return categoryMapper.findList();
    }
}