package com.datvm.hairbookingapp.exception;

import com.datvm.hairbookingapp.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Neu nhu bat duoc cac exception khac ngoai nhung cai duoc cau hinh se duoc handling vo day
//    @ExceptionHandler(value = Exception.class)
//    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception){
//        ApiResponse apiResponse = new ApiResponse();
//
//        apiResponse.setCode(ErrorCode.UNCATEGORIZE_EXCEPTION.getCode());
//        apiResponse.setMessage(ErrorCode.UNCATEGORIZE_EXCEPTION.getMessage());
//        return ResponseEntity.badRequest().body(apiResponse);
//    }


    //Neu co bat cu exception loai AppException thi se duoc dieu huong vo day
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }


    //Handling cac exception cua validation anotation
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
        String msg = "";
        for(FieldError fieldError :exception.getBindingResult().getFieldErrors()){
            //loop qua từng field của dữ liệu , nếu cái nào có lỗi thì thêm vào msg
            msg += fieldError.getDefaultMessage()+"\n";

        }

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(400);
        apiResponse.setMessage(msg);

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
