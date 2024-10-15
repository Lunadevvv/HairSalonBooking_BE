package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.CreateStaffRequest;
import com.datvm.hairbookingapp.dto.response.StaffResponse;
import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.entity.Role;
import com.datvm.hairbookingapp.entity.Staff;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.AccountMapper;
import com.datvm.hairbookingapp.repository.AuthenticationRepository;
import com.datvm.hairbookingapp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountExpiredException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void deleteStaff(String code){
        Staff staff = staffRepository.findStaffByCode(code);
        if(staff == null)
            throw new AppException(ErrorCode.STAFF_NOT_FOUND);
        staffRepository.delete(staff);
    }

    public List<Staff> getAllStaff(){
        List<Staff> list = staffRepository.findAll();
        return list;
    }

    public StaffResponse getStaffByCode(String code){
        Staff staff = staffRepository.findStaffByCode(code);
        if(staff == null)
            throw new AppException(ErrorCode.STAFF_NOT_FOUND);
        return accountMapper.toStaffRes(staff);
    }

    public StaffResponse createStaff(CreateStaffRequest request) throws HttpMessageNotReadableException {
            String code = generateStaffCode();
            if(request.getRole() == null)
                request.setRole(Role.STAFF);
            Staff staff = accountMapper.toStaff(request);
            staff.setCode(code);
            try{
                staff.setAccount(authenticationRepository.save(Account.builder()
                        .email(staff.getEmail())
                        .staff(staff)
                        .role(staff.getRole())
                        .phone(staff.getPhone())
                        .lastName(staff.getLastName())
                        .firstName(staff.getFirstName())
                        .password(passwordEncoder.encode("staff123"))
                        .build()
                ));
                staff = staffRepository.save(staff);
            }catch (AppException e){
                throw new AppException(ErrorCode.PROCESS_FAILED);
            }

            return accountMapper.toStaffRes(staff);
    }

    public StaffResponse updateStaffProfile(CreateStaffRequest request, String code){
        Staff staff = staffRepository.findStaffByCode(code);
        if(staff == null)
            throw new AppException(ErrorCode.STAFF_NOT_FOUND);
        staff.setGender(request.getGender());
        staff.setEmail(request.getEmail());
        staff.setYob(request.getYob());
        staff.setFirstName(request.getFirstName());
        staff.setLastName(request.getLastName());
        staff.setPhone(request.getPhone());
        staff.setJoinIn(request.getJoinIn());
        if(request.getRole() == null)
            request.setRole(Role.STAFF);
        else
            staff.setRole(request.getRole());
        try{
            return accountMapper.toStaffRes(staffRepository.save(staff));
        }catch (AppException e) {
            throw new AppException(ErrorCode.PROCESS_FAILED);
        }
    }

    public String generateStaffCode(){
        String code = "S0001";
        String latestCode = staffRepository.findTheLatestStaffCode();
        if(latestCode == null)
            return code;
        int fourLastChar = Integer.parseInt(latestCode.substring(1));
        code = String.format("S%04d", ++fourLastChar);
        return code;
    }
}