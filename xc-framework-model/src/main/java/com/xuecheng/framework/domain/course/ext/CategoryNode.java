package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.Category;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Daniel Liu 2020/4/27 17:07
 */
@Data
@ToString
public class CategoryNode extends Category {
    List<CategoryNode> children;
}