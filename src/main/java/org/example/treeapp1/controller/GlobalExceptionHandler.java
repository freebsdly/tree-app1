package org.example.treeapp1.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.treeapp1.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理器
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{

    /**
     * 处理所有自定义业务异常
     */
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiBody<Object> handleBaseException(BaseException ex)
    {
        log.error("业务异常: {}", ex.getMessage(), ex);
        return ApiBody.failure(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理其他未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiBody<Object> handleAllExceptions(Exception ex)
    {
        log.error("系统异常: {}", ex.getMessage(), ex);
        return ApiBody.failure(500, ex.getMessage());
    }
}
