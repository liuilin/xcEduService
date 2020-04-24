package com.xuecheng.framework.model.r;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Daniel Liu 2020/4/22 15:20
 */
@Data
@NoArgsConstructor
@ToString
public class ResponseResult implements Response {

    int code = SUCCESS_CODE;
    String message;
    boolean success = SUCCESS;

    public ResponseResult(ResultCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.success = resultCode.success();
    }


}