package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.CreateStaffRequest;
import com.datvm.hairbookingapp.dto.response.StaffResponse;
import com.datvm.hairbookingapp.entity.Role;
import com.datvm.hairbookingapp.entity.Staff;
import com.datvm.hairbookingapp.mapper.AccountMapper;
import com.datvm.hairbookingapp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    AccountMapper accountMapper;

    public StaffResponse createStaff(CreateStaffRequest request){
        String code = generateStaffCode();
        if(request.getRole() == null)
            request.setRole(Role.STAFF);
        Staff staff = accountMapper.toStaff(request);
        staff.setCode(code);
        return accountMapper.toStaffRes(staffRepository.save(staff));
    }

    public String generateStaffCode(){
        String code = "S0001";
        String latestCode = staffRepository.findTheLatestStaffCode();
        if(latestCode != null)
            return code;
        int fourLastChar = Integer.parseInt(latestCode.substring(1));
        code = String.format("S%04d", ++fourLastChar);
        return code;
    }
}
