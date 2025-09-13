package org.example.treeapp1.controller;

public record ApiBody<T>(int code, String message, T data)
{
    public static <T> ApiBody<T> success(T data)
    {
        return new ApiBody<>(0, "success", data);
    }

    public static <T> ApiBody<T> failure(int code, String message)
    {
        return new ApiBody<>(code, message, null);
    }
}
