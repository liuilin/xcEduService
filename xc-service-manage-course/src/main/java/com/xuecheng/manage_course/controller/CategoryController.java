package com.xuecheng.manage_course.controller;

import com.xuecheng.api.CategoryControllerApi;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.service.CategoryService;

/**
 * @author Daniel Liu 2020/4/27 17:15
 */
public class CategoryController implements CategoryControllerApi {
    private CategoryService categoryService;

    @Override
    public CategoryNode findList() {
        return categoryService.findList();
    }
}