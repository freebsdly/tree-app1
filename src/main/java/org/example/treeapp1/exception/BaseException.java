package org.example.treeapp1.exception;

import lombok.Getter;

/**
 * 自定义异常基类
 */
@Getter
public class BaseException extends RuntimeException
{
    private final int code;

    public BaseException(int code, String message)
    {
        super(message);
        this.code = code;
    }

}
