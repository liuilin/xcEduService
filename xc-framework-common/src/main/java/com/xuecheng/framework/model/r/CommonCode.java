package com.xuecheng.framework.model.r;

/**
 * @author Daniel Liu 2020/4/22 14:52
 */
public enum CommonCode implements ResultCode {
    SUCCESS(10000,"操作成功",true),
    ;


    private int code;

    private String message;

    private boolean success;

    CommonCode(int code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public boolean success() {
        return success;
    }
}