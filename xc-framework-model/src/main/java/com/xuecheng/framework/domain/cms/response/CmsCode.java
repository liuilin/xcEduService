package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.model.response.ResultCode;
import lombok.ToString;

/**
 * @author Daniel Liu 2019/11/27 9:25
 */
@ToString
public enum CmsCode implements ResultCode {
    CMS_PAGE_NAME_EXITS(false, 24001, "页面已存在"),
    ;

    boolean success;
    int code;
    String message;

    CmsCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
