package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.UpdateProfileRequest;
import com.datvm.hairbookingapp.dto.response.AccountResponse;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.service.AuthenticationService;
import com.datvm.hairbookingapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequestMapping("/api/v1/profile/")
public class ProfileController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ProfileService profileService;

    @GetMapping
    public ApiResponse<AccountResponse> getMyProfile(){
        return ApiResponse.<AccountResponse>builder()
                .code(200)
                .result(profileService.getMyProfile())
                .build();
    }

    @PutMapping
    public ApiResponse<AccountResponse> updateMyProfile(@RequestBody UpdateProfileRequest request) throws SQLIntegrityConstraintViolationException {
        return ApiResponse.<AccountResponse>builder()
                .code(200)
                .result(profileService.updateMyProfile(request))
                .message("Cập nhật thông tin thành công!")
                .build();
    }

}
