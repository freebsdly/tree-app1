package org.example.treeapp1.exception;

public class ResourceNotFoundException extends BusinessException
{
    private static final int code = 404;

    public ResourceNotFoundException(String message)
    {
        super(code, String.format("resource not found: %s", message));
    }
}
