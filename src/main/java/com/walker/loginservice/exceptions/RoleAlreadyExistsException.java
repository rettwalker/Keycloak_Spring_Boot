package com.walker.loginservice.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created by rettwalker on 2/10/17.
 */
public class RoleAlreadyExistsException extends Exception {
    private String message;
    private int responseCode;

    public RoleAlreadyExistsException(String message){
        this.message = message;
        this.responseCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
