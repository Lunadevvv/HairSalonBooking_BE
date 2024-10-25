package com.datvm.hairbookingapp.exception;

public enum ErrorCode {
    PROCESS_FAILED(9998, "Có lỗi trong quá trình!"),
    ACCOUNT_NOT_FOUND(9999, "Tài khoản không có!"),
    TOKEN_WRONG(1001, "Token không đúng!"),
    TOKEN_EXPIRED(1003, "Token đã hết hạn!"),
    DUPLICATE_EMAIL(1000,"Email này không khả dụng!"),
    USER_NOT_EXISTED(1005, "Người dùng này không tồn tại!"),
    TOKEN_MISSING(1006, "Token is missing"),
    DUPLICATE_PHONE(1000, "Số điện thoại đã được sử dụng"),
    CATEGORY_EXISTED(1002, "Category đã tồn tại"),
    CATEGORY_NOT_EXISTED(1005, "Category không tồn tại"),
    SERVICES_EXISTED(1002, "Dịch vụ đã tồn tại"),
    SERVICES_NOT_EXISTED(1005, "Dịch vụ này không tồn tại"),
    INVALID_IMAGE(1007,"Đường dẫn ảnh không đúng"),
    STAFF_NOT_FOUND(9999, "Không tìm thấy nhân viên này!"),
    COMBO_NOT_FOUND(9999, "Combo không có!"),
    PASSWORD_WRONG(9999, "Sai mật khẩu!"),
    EMPTY_SLOT(9999, "Không có Slot nào!"),
    STYLIST_ONLY(9999, "Nhân viên này không phải Stylist!"),
    NO_AVAILABLE_STYLISTS(9999, "Không có Stylist nào trống!"),
    BOOKING_NOT_FOUND(9999, "Không tìm thầy Booking này!"),
    BOOKING_FULL(9999,"Slot này đã full booking"),
    CANT_SUBMIT_BOOKING(9999, "Bạn có đơn đặt lịch khác đang chờ!"),
    DUPLICATE_COMBO(9999, "Trùng combo rồi!"),
    FEEDBACK_NOT_FOUND(9999,"Không tìm thấy feedback")
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
