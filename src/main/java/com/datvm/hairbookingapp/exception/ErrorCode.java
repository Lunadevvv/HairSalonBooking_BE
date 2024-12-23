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
    SALON_NOT_FOUND(9999,"Không tim thấy salon"),
    SALON_CLOSED(9999,"Salon này đã đóng cửa!"),
    MANAGER_NOT_FOUND(9999,"Không tìm thấy Manager"),
    MANAGER_ALREADY(9999,"Bạn đã là Manager"),
    INVALID_ACTION(9999,"Salon này đã có manager"),
    DUPLICATE_COMBO(9999, "Trùng combo rồi!"),
    FEEDBACK_NOT_FOUND(9999,"Không tìm thấy feedback"),
    PAYMENT_NOT_FOUND(9999,"Không tìm thấy payment"),
    PAYMENT_ALREADY_DONE(9999,"Payment này đã được thanh toán"),
    PAYMENT_INVALID_SUBMIT(9999,"Không thể thanh toán payment này"),
    SERVICES_NOT_ACTIVE(9999,"Không thể sử dụng service này"),
    BOOKING_INVALID_CANCELLED(9999,"Không thể cancel booking này"),
    STAFF_NOT_ACTIVE(9999,"Không thể sử dụng staff này"),
    STAFF_INVALID_ACTION(9999,"Staff này đã bị disabled"),
    FEEDBACK_IS_DONE(9999,"feedback này đã được hoàn thành")
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
