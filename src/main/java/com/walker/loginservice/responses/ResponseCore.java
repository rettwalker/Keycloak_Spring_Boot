package com.walker.loginservice.responses;

/**
 * Created by rettwalker on 2/10/17.
 */
public class ResponseCore {

    private int responseCode;

    private String message;

    private boolean errorState;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isErrorState() {
        return errorState;
    }

    public void setErrorState(boolean errorState) {
        this.errorState = errorState;
    }
}
