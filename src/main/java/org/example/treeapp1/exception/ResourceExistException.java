package org.example.treeapp1.exception;

public class ResourceExistException extends BusinessException
{
    private static final int code = 409;

    public ResourceExistException(String message)
    {
        super(code, String.format("resource already exist: %s", message));
    }
}
