package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.CreateStaffRequest;
import com.datvm.hairbookingapp.dto.request.PromoteStaffRequest;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.dto.response.ManagerResponse;
import com.datvm.hairbookingapp.service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manager")
public class ManagerController {
    @Autowired
    ManagerService managerService;

//    @PostMapping
//    public ApiResponse<ManagerResponse> createManager(@RequestBody @Valid CreateStaffRequest request){
//        return ApiResponse.<ManagerResponse>builder()
//                .result(managerService.createManager(request))
//                .build();
//    }

    @GetMapping("/{id}")
    public ApiResponse<ManagerResponse> getManagerById(@PathVariable String id){
        return ApiResponse.<ManagerResponse>builder()
                .result(managerService.getManagerById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ManagerResponse>> getManager(){
        return ApiResponse.<List<ManagerResponse>>builder()
                .result(managerService.getAllManagers())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ManagerResponse> updateManagerById(@RequestBody @Valid PromoteStaffRequest request, @PathVariable String id){
        return ApiResponse.<ManagerResponse>builder()
//                .result(managerService.updateManagerInfo(request,id))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteManagerById(@PathVariable String id){
        return ApiResponse.<String>builder()
                .result(managerService.deleteManager(id))
                .build();
    }

    @DeleteMapping("/degrade/{id}")
    public ApiResponse<String> deGradeManagerById(@PathVariable String id){
        return ApiResponse.<String>builder()
                .result(managerService.deGradeManager(id))
                .build();
    }
    @DeleteMapping("/remove/{id}")
    public ApiResponse<String> removeManageById(@PathVariable String id){
        return ApiResponse.<String>builder()
                .result(managerService.removeManage(id))
                .build();
    }

}
