package com.chifanhero.api.models.response;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by shiyan on 5/6/17.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
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
