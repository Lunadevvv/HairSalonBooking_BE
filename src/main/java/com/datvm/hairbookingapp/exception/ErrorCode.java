package com.datvm.hairbookingapp.exception;

public enum ErrorCode {
    UNCATEGORIZE_EXCEPTION(9999, "Uncategorize exception"),
    TOKEN_WRONG(1001, "Token is wrong"),
    TOKEN_EXPIRED(1003, "Token is expired"),
    DUPLICATE_EMAIL(1000,"Duplicate Email"),
    USER_NOT_EXISTED(1005, "User not existed"),
    TOKEN_MISSING(1006, "Token is missing"),
    DUPLICATE_PHONE(1000, "Duplicate Phone"),
    SALON_NOT_EXISTED(1005, "Salon not existed"),
    SALON_EXISTED(1002, "Salon existed"),
    ;

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
