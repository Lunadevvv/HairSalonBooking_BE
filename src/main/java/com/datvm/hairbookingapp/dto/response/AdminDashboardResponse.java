package com.datvm.hairbookingapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDashboardResponse {
    private int totalSales;
    private int totalStaffs;
    private int totalSalons;
    private List<StaffResponse> topFiveStaffByRating;
    private List<RevenueSalesResponse> revenueSalesResponses;
}
