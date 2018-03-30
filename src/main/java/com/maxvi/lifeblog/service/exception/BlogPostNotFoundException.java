package com.maxvi.lifeblog.service.exception;

public class BlogPostNotFoundException extends Exception
{
    public BlogPostNotFoundException(String message)
    {
        super(message);
    }
}
