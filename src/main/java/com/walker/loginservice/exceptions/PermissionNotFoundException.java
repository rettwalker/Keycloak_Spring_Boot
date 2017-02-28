package com.walker.loginservice.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created by jmw0705 on 2/17/17.
 */
public class PermissionNotFoundException extends Exception {
    private String message;
    private int responseCode;

    public PermissionNotFoundException(String message){
        this.message = message;
        this.responseCode = HttpStatus.NOT_FOUND.value();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
