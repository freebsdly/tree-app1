package org.example.treeapp1.exception;

public class ResourceExistException extends BaseException
{
    private static final int code = 409;

    public ResourceExistException(String message)
    {
        super(code, message);
    }
}
