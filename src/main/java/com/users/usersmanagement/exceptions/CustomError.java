package com.users.usersmanagement.exceptions;

import org.springframework.web.context.request.WebRequest;

import java.util.Date;

public class CustomError {
    private Integer statusCode;
    private String message;

    public CustomError(){}
    public CustomError(Integer statusCode, String message){
        this.statusCode= statusCode;
        this.message=message;
    }
    public void setStatusCode(Integer statusCode){
        this.statusCode=statusCode;
    }
    public void setMessage(String message){
        this.message=message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}

