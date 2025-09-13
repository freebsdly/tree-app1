package org.example.treeapp1.exception;

import lombok.Getter;

/**
 * 自定义异常基类
 */
@Getter
public class BusinessException extends Exception
{
    private final int code;

    public BusinessException(int code, String message)
    {
        super(message);
        this.code = code;
    }

}
