package com.xuecheng.api;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.ApiOperation;

/**
 * @author Daniel Liu 2020/4/27 17:11
 */
public interface CategoryControllerApi {
    @ApiOperation("查询分类")
    CategoryNode findList();
}
