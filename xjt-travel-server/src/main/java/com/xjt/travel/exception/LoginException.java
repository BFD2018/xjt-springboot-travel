package com.xjt.travel.exception;

public class LoginException extends RuntimeException{
    protected final String message;

    public LoginException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
