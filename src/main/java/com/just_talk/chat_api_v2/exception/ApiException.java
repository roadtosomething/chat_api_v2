package com.just_talk.chat_api_v2.exception;

public class ApiException extends RuntimeException{
    protected String errorCode;

    public ApiException(String message, String errorCode){
        super(message);
        this.errorCode=errorCode;
    }
}
