package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.Category;

import java.util.List;

/**
 * @author Daniel Liu 2020/4/27 17:07
 */
public class CategoryNode extends Category {
    List<CategoryNode> children;
}