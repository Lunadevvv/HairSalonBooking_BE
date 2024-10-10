package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.CreateStaffRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.StaffResponse;
import com.datvm.hairbookingapp.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/staff/")
public class StaffController {

    @Autowired
    StaffService staffService;

    @PostMapping
    public ApiResponse<StaffResponse> createStaff(@RequestBody CreateStaffRequest request){
        return ApiResponse.<StaffResponse>builder()
                .code(200)
                .result(staffService.createStaff(request))
                .message("Tạo nhân viên thành công!")
                .build();
    }
}
