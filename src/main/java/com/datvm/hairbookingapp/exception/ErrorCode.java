package com.datvm.hairbookingapp.exception;

public enum ErrorCode {
    UNCATEGORIZE_EXCEPTION(9999, "Uncategorize exception"),
    USER_EXISTED(1001, "User existed"),
    PASSWORD_INVALID(1003, "Password must be at least 8 characters"),
    DUPLICATE_EMAIL(1000,"Duplicate Email"),
    USER_NOT_EXISTED(1005, "User not existed"),
    UNAUTHENTICATED(1006, "Unauthenticated"),
    DUPLICATE_PHONE(1000, "Duplicat Phone"),

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
