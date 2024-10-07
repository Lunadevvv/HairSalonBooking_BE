package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.ForgotRequest;
import com.datvm.hairbookingapp.dto.request.RegisterRequest;
import com.datvm.hairbookingapp.dto.request.LoginRequest;
import com.datvm.hairbookingapp.dto.request.ResetPasswordRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.LoginResponse;
import com.datvm.hairbookingapp.dto.response.RegisterResponse;
import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.repository.AuthenticationRepository;
import com.datvm.hairbookingapp.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    ApiResponse<RegisterResponse> createAccount(@RequestBody @Valid RegisterRequest request){
        return ApiResponse.<RegisterResponse>builder()
                .code(201)
                .result(authenticationService.createAccount(request))
                .build();
    }

    @PostMapping("/login")
    ApiResponse<LoginResponse> loginAccount(@RequestBody LoginRequest request){
        LoginResponse response = authenticationService.login(request);
        return ApiResponse.<LoginResponse>builder()
                .code(200)
                .result(response)
                .build();
    }

    @PostMapping("/forgot-password")
    ApiResponse forgotPassword(@RequestBody @Valid ForgotRequest forgotRequest){
        authenticationService.forgotPassword(forgotRequest.getPhone());
        return ApiResponse.builder()
                .code(200)
                .message("Check your email to confirm reset password")
                .build();
    }

    @PostMapping("/reset-password")
    ApiResponse resetPassword(@RequestBody @Valid ResetPasswordRequest request){
        boolean status = authenticationService.resetPassword(request);
        if (!status) {
            throw new AppException(ErrorCode.PROCESS_FAILED);
        }
        else
            return ApiResponse.builder()
                    .code(200)
                    .message("Reset Password Successfully!")
                    .build();
    }


}
