package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.response.AdminDashboardResponse;
import com.datvm.hairbookingapp.dto.response.RevenueSalesResponse;
import com.datvm.hairbookingapp.dto.response.StaffResponse;
import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.entity.Salon;
import com.datvm.hairbookingapp.mapper.AccountMapper;
import com.datvm.hairbookingapp.repository.BookingRepository;
import com.datvm.hairbookingapp.repository.SalonRepository;
import com.datvm.hairbookingapp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    SalonRepository salonRepository;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AuthenticationService authenticationService;

    public AdminDashboardResponse AdminDashboard(){
        int countTotalStaff = staffRepository.countTotalStaff();
        int countTotalSales = bookingRepository.countTotalSales();
        int countTotalSalons = salonRepository.countTotalSalons();
        List<StaffResponse> topFiveStylistByRating = staffRepository.topFiveStylistByRating()
                .stream().map(accountMapper::toStaffRes).toList();
        List<RevenueSalesResponse> monthlyRevenue = new ArrayList<>();
        List<Object[]> revenueSalesResponses = bookingRepository.revenueSales();

        for(Object[] obj: revenueSalesResponses){
            RevenueSalesResponse o = new RevenueSalesResponse();
            o.setMonth(Integer.parseInt(obj[0].toString()));
            o.setYear(Integer.parseInt(obj[1].toString()));
            o.setSales(Integer.parseInt(obj[2].toString()));
            monthlyRevenue.add(o);
        }

        return AdminDashboardResponse.builder()
                .totalSalons(countTotalSalons)
                .totalStaffs(countTotalStaff)
                .totalSales(countTotalSales)
                .topFiveStaffByRating(topFiveStylistByRating)
                .revenueSalesResponses(monthlyRevenue)
                .build();
    }

    public AdminDashboardResponse ManagerDashboard(){
        Account account = authenticationService.getCurrentAccount();
        Salon salon = account.getStaff().getSalons();
        int countTotalStaff = staffRepository.countTotalStaffBySalon(salon);
        int countTotalSales = bookingRepository.countTotalSalesBySalon(salon.getId());
        int countTotalSalons = 0;
        List<StaffResponse> topFiveStylistByRating = staffRepository.topFiveStylistByRatingAndSalon(salon)
                .stream().map(accountMapper::toStaffRes).toList();
        List<RevenueSalesResponse> monthlyRevenue = new ArrayList<>();
        List<Object[]> revenueSalesResponses = bookingRepository.revenueSalesBySalon(salon.getId());

        for(Object[] obj: revenueSalesResponses){
            RevenueSalesResponse o = new RevenueSalesResponse();
            o.setMonth(Integer.parseInt(obj[0].toString()));
            o.setYear(Integer.parseInt(obj[1].toString()));
            o.setSales(Integer.parseInt(obj[2].toString()));
            monthlyRevenue.add(o);
        }

        return AdminDashboardResponse.builder()
                .totalSalons(countTotalSalons)
                .totalStaffs(countTotalStaff)
                .totalSales(countTotalSales)
                .topFiveStaffByRating(topFiveStylistByRating)
                .revenueSalesResponses(monthlyRevenue)
                .build();
    }
}
