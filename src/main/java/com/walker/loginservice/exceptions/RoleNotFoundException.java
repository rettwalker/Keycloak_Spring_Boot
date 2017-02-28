package com.walker.loginservice.exceptions;

/**
 * Created by jmw0705 on 2/13/17.
 */
public class RoleNotFoundException extends Exception {
    private String message;
    private int responseCode;

    public RoleNotFoundException(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
