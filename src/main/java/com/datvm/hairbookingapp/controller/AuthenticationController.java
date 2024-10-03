package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.AuthenticationRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    ApiResponse<Account> createAccount(@RequestBody @Valid AuthenticationRequest request){
        return ApiResponse.<Account>builder()
                .result(authenticationService.createAccount(request))
                .build();
    }

}
