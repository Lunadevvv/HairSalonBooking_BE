package com.datvm.hairbookingapp.exception;

public enum ErrorCode {
    PROCESS_FAILED(9998, "Process failed!"),
    ACCOUNT_NOT_FOUND(9999, "Account not Found!"),
    TOKEN_WRONG(1001, "Token is wrong"),
    TOKEN_EXPIRED(1003, "Token is expired"),
    DUPLICATE_EMAIL(1000,"Duplicate Email"),
    USER_NOT_EXISTED(1005, "User not existed"),
    TOKEN_MISSING(1006, "Token is missing"),
    DUPLICATE_PHONE(1000, "Duplicate Phone"),
    CATEGORY_EXISTED(1002, "Category already existed"),
    CATEGORY_NOT_EXISTED(1005, "Category not existed"),
    SERVICES_EXISTED(1002, "Services already existed"),
    SERVICES_NOT_EXISTED(1005, "Service not existed"),
    INVALID_IMAGE(1007,"Invalid image format"),
    STAFF_NOT_FOUND(9999, "Staff not found"),
    COMBO_NOT_FOUND(9999, "Combo not found"),
    PASSWORD_WRONG(9999, "Password wrong!"),
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
