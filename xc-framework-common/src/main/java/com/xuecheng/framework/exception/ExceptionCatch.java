package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理（要把本包让启动类扫描到，这样注解才生效 - 可用断点方式验证）
 *
 * @author Daniel Liu 2019/11/27 10:06
 */
@ControllerAdvice //控制器增强
public class ExceptionCatch {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);


    /**
     * 使用 EXCEPTIONS 存放异常类型和错误代码的映射，ImmutableMap 的特点的一旦创建不可改变，并且线程安全
     */
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;

    /**
     * 使用 builder 来构建一个异常类型和错误代码的异常
     */
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    static {
        builder.put(HttpMediaTypeNotSupportedException.class, CommonCode.INVALID_PARAMETER);
    }

    /**
     * 捕获 CustomException 可预知异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler( CustomException.class )
    @ResponseBody //返回给前端 json 数据
    private ResponseResult customException(CustomException e) {
        //记录日志
        LOGGER.error("catch exception:{}", e.getMessage(), e);
        return new ResponseResult(e.getResultCode());
    }

    /**
     * 捕获 Exception 不可以预知异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler
    public ResponseResult exception(Exception e) {
        LOGGER.error("catch exception", e.getMessage(), e);
        if (EXCEPTIONS == null) {
            EXCEPTIONS = builder.build();
        }
        ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        if (resultCode != null) {
            return new ResponseResult(resultCode);
        }
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }
}