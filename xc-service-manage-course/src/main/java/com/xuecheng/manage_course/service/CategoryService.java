package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Daniel Liu 2020/4/27 17:17
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public CategoryNode findList() {
        return categoryMapper.findList();
    }
}