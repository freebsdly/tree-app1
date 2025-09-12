package org.example.treeapp1.exception;

public class ResourceNotFoundException extends BaseException
{
    private static final int code = 404;

    public ResourceNotFoundException(String message)
    {
        super(code, message);
    }
}
