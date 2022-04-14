package com.yetkin.sepet.exception.errors;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    ENTITY_NOT_FOUND_EXCEPTION(1, "ENTITY_NOT_FOUND_EXCEPTION"),

    NOT_ENOUGH_STOCK(2, "NOT_ENOUGH_STOCK"),
    REQUIRED_FIELD(3, "REQUIRED_FIELD"),
    FOREIGN_KEY_EXCEPTION(4, "FOREIGN_KEY_EXCEPTION"),


    NOT_KNOWN(999, "NOT_KNOWN");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
