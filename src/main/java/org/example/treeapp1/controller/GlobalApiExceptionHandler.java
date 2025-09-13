package org.example.treeapp1.controller;

import org.example.treeapp1.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 全局异常处理器
 * 处理所有自定义业务异常和其他未捕获异常
 */
@ControllerAdvice
public class GlobalApiExceptionHandler extends ResponseEntityExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalApiExceptionHandler.class);

    /**
     * 处理所有自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiBody<Object>> handleBaseException(BusinessException ex)
    {
        log.error("business exception: {}", ex.getMessage());
        ApiBody<Object> failure = ApiBody.failure(ex.getCode(), ex.getMessage());
        return ResponseEntity.ok(failure);
    }

    /**
     * 处理其他未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiBody<Object>> handleAllExceptions(Exception ex)
    {
        log.error("system exception: {}", ex.getMessage(), ex);
        ApiBody<Object> failure = ApiBody.failure(500, ex.getMessage());
        return ResponseEntity.internalServerError().body(failure);
    }
}
