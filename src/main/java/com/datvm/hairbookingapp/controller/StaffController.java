package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.CreateStaffRequest;
import com.datvm.hairbookingapp.dto.request.DateAndSlotRequest;
import com.datvm.hairbookingapp.dto.request.PromoteStaffRequest;
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
//@PreAuthorize("hasAuthority('ADMIN')")
public class StaffController {

    @Autowired
    StaffService staffService;

    @PutMapping("/promote/{code}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse promoteToManager(@PathVariable String code, @RequestBody @Valid PromoteStaffRequest request){
        staffService.promoteToManager(code, request);
        return ApiResponse.builder()
                .code(200)
                .message("Thăng cấp cho nhân viên " + code + " thành công!")
                .build();
    }

    @PostMapping
    public ApiResponse<StaffResponse> createStaff(@RequestBody @Valid CreateStaffRequest request)
            throws HttpMessageNotReadableException {
        return ApiResponse.<StaffResponse>builder()
                .code(200)
                .result(staffService.createStaff(request))
                .message("Tạo nhân viên thành công!")
                .build();
    }

    @PutMapping("/{code}")
    public ApiResponse<StaffResponse> updateStaffProfile(@RequestBody @Valid CreateStaffRequest request,
                                                         @PathVariable String code){
        return ApiResponse.<StaffResponse>builder()
                .code(200)
                .message("Update nhân viên: " + code +" thành công!")
                .result(staffService.updateStaffProfile(request, code))
                .build();
    }

    @GetMapping
    public ApiResponse<List<Staff>> getAllStaff(){
        return ApiResponse.<List<Staff>>builder()
                .code(200)
                .result(staffService.getAllStaff())
                .build();
    }

    @GetMapping("{code}")
    public ApiResponse<StaffResponse> getAllStaff(@PathVariable String code){
        return ApiResponse.<StaffResponse>builder()
                .code(200)
                .result(staffService.getStaffByCode(code))
                .build();
    }

    @DeleteMapping("{code}")
    public ApiResponse deleteStaff(@PathVariable String code){
        staffService.deleteStaff(code);
        return ApiResponse.builder()
                .code(200)
                .message("Xoá nhân viên có mã số " + code + " thành công!")
                .build();
    }

    @GetMapping("/stylist")
    public ApiResponse<List<StaffResponse>> getAvailableStylist(@RequestBody @Valid DateAndSlotRequest request){
        return ApiResponse.<List<StaffResponse>>builder()
                .code(200)
                .result(staffService.getAvailableStylist(request))
                .build();
    }
}
