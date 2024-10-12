package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.CreateStaffRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.StaffResponse;
import com.datvm.hairbookingapp.entity.Staff;
import com.datvm.hairbookingapp.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
@PreAuthorize("hasAuthority('ADMIN')")
public class StaffController {

    @Autowired
    StaffService staffService;

    @PostMapping
    public ApiResponse<StaffResponse> createStaff(@RequestBody @Valid CreateStaffRequest request)
            throws HttpMessageNotReadableException {
        return ApiResponse.<StaffResponse>builder()
                .code(200)
                .result(staffService.createStaff(request))
                .message("Tạo nhân viên thành công!")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<StaffResponse> updateStaffProfile(@RequestBody @Valid CreateStaffRequest request,
                                                         @PathVariable Long id){
        return ApiResponse.<StaffResponse>builder()
                .code(200)
                .message("Update id: " + id +" thành công!")
                .result(staffService.updateStaffProfile(request, id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<Staff>> getAllStaff(){
        return ApiResponse.<List<Staff>>builder()
                .code(200)
                .result(staffService.getAllStaff())
                .build();
    }
}
