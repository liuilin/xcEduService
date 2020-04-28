package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;

/**
 * @author Daniel Liu 2020/4/28 17:27
 */
@Data
public class CourseResult extends ResponseResult {
    CourseBase courseBase;

    public CourseResult(ResultCode resultCode,CourseBase courseBase) {
        super(resultCode);
        this.courseBase = courseBase;
    }
}