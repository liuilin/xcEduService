package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;
import lombok.Getter;

/**
 * 自定义异常，继承RuntimeException使得对代码无侵入行，若是继承Exception，在throw new Exception时则需要try/catch或是抛出
 *
 * 系统对异常的处理使用统一的异常处理流程：
 * 1、自定义异常类型。
 * 2、自定义错误代码及错误信息。
 * 3、对于可预知的异常由程序员在代码中主动抛出，由SpringMVC统一捕获。可预知异常是程序员在代码中手动抛出本系统定义的特定异常类型，
 *    由于是程序员抛出的异常，通常异常信息比较齐全，程序员在抛出时会指定错误代码及错误信息，获取异常信息也比较方便。
 * 4、对于不可预知的异常（运行时异常）由SpringMVC统一捕获Exception类型的异常。
 * 不可预知异常通常是由于系统出现bug、或一些不要抗拒的错误（比如网络中断、服务器宕机等），异常类型为RuntimeException类型（运行时异常）。
 * 5、可预知的异常及不可预知的运行时异常最终会采用统一的信息格式（错误代码+错误信息）来表示，最终也会随请求响应给客户端。
 *
 * @author Daniel Liu 2019/11/27 8:09
 */
@Getter
public class CustomException extends RuntimeException {
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        //异常信息为：错误代码+异常信息
        super("错误代码：" + resultCode.code() + ";异常信息：" + resultCode.message());
        this.resultCode = resultCode;
    }
}