package com.chifanhero.api.models.response;

/**
 * Created by shiyan on 5/6/17.
 */
public class Error {

    private String message;
    private ErrorLevel level;

    public Error(String message, ErrorLevel errorLevel) {
        this.message = message;
        this.level = errorLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorLevel getLevel() {
        return level;
    }

    public void setLevel(ErrorLevel level) {
        this.level = level;
    }
}
