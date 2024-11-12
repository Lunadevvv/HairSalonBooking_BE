package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.response.AdminDashboardResponse;
import com.datvm.hairbookingapp.dto.response.ApiResponse;
import com.datvm.hairbookingapp.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<AdminDashboardResponse> getAdminStats(){
        return ApiResponse.<AdminDashboardResponse>builder()
                .code(200)
                .result(dashboardService.AdminDashboard())
                .build();
    }

    @GetMapping("/manager")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ApiResponse<AdminDashboardResponse> getManagerStats(){
        return ApiResponse.<AdminDashboardResponse>builder()
                .code(200)
                .result(dashboardService.ManagerDashboard())
                .build();
    }
}
