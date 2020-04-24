package com.xuecheng.framework.model.r;

/**
 * @author Daniel Liu 2020/4/22 14:49
 */
public interface ResultCode {
    int code();

    String message();

    boolean success();
}
