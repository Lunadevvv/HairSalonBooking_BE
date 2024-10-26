package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.CreateStaffRequest;
import com.datvm.hairbookingapp.dto.request.DateAndSlotRequest;
import com.datvm.hairbookingapp.dto.request.PromoteStaffRequest;
import com.datvm.hairbookingapp.dto.response.StaffResponse;
import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.entity.Salon;
import com.datvm.hairbookingapp.entity.enums.Role;
import com.datvm.hairbookingapp.entity.Staff;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.AccountMapper;
import com.datvm.hairbookingapp.repository.AuthenticationRepository;
import com.datvm.hairbookingapp.repository.SalonRepository;
import com.datvm.hairbookingapp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    SalonRepository salonRepository;

    @Autowired
    ManagerService managerService;

    public List<StaffResponse> getAvailableStylist(DateAndSlotRequest request) {
        List<Staff> list = staffRepository.findAvailableStylists(request.getSlotId(), request.getDate(), Role.STYLIST);
        return list.stream().map(accountMapper::toStaffRes).collect(Collectors.toList());
    }

    public void deleteStaff(String code) {
        Staff staff = staffRepository.findStaffByCode(code);
        if (staff == null)
            throw new AppException(ErrorCode.STAFF_NOT_FOUND);
        Account account = authenticationRepository.findAccountByPhone(staff.getPhone());
        try {
            staff.setActive(false);
            staffRepository.save(staff);
            authenticationRepository.delete(account);
        } catch (AppException e) {
            throw new AppException(ErrorCode.PROCESS_FAILED);
        }
    }

    public List<Staff> getAllStaff() {
        List<Staff> list = staffRepository.findAll();
        return list;
    }

    public StaffResponse getStaffByCode(String code) {
        Staff staff = staffRepository.findStaffByCode(code);
        if (staff == null)
            throw new AppException(ErrorCode.STAFF_NOT_FOUND);
        return accountMapper.toStaffRes(staff);
    }

    public StaffResponse createStaff(CreateStaffRequest request) throws HttpMessageNotReadableException {
        Salon salon = salonRepository.findById(request.getSalonId()).orElseThrow( () -> new AppException(ErrorCode.SALON_NOT_FOUND));
        String code = generateStaffCode();
        if (request.getRole() == null)
            request.setRole(Role.STAFF);
        Staff staff = accountMapper.toStaff(request);
        staff.setCode(code);
        staff.setImage(request.getImage());
        staff.setRole(request.getRole());
        staff.setSalons(salon);
        staff.setActive(true);
        try {
            staff.setAccount(authenticationRepository.save(Account.builder()
                    .email(staff.getEmail())
                    .staff(staff)
                    .role(request.getRole())
                    .phone(staff.getPhone())
                    .lastName(staff.getLastName())
                    .firstName(staff.getFirstName())
                    .password(passwordEncoder.encode("staff123"))
                    .build()
            ));
            staff = staffRepository.save(staff);
        } catch (AppException e) {
            throw new AppException(ErrorCode.PROCESS_FAILED);
        }
        StaffResponse res = accountMapper.toStaffRes(staff);
        res.setImage(staff.getImage());
        res.setSalons(staff.getSalons());
        return res;
    }


    public StaffResponse updateStaffProfile(CreateStaffRequest request, String code) {
        Staff staff = staffRepository.findStaffByCode(code);
        if (staff == null)
            throw new AppException(ErrorCode.STAFF_NOT_FOUND);
        Salon salon = salonRepository.findById(request.getSalonId()).orElseThrow( () -> new AppException(ErrorCode.SALON_NOT_FOUND));
        staff.setGender(request.getGender());
        staff.setEmail(request.getEmail());
        staff.setYob(request.getYob());
        staff.setFirstName(request.getFirstName());
        staff.setLastName(request.getLastName());
        staff.setPhone(request.getPhone());
        staff.setJoinIn(request.getJoinIn());
        staff.setImage(request.getImage());
//        staff.setRole(request.getRole());
        staff.setSalons(salon);
        Account account = staff.getAccount();
        if (account != null) {
            account.setFirstName(request.getFirstName());
            account.setLastName(request.getLastName());
            account.setEmail(request.getEmail());
            account.setPhone(request.getPhone());
//            if (!account.getRole().equals(request.getRole())) {
//                account.setRole(request.getRole());
//            }
        }

        try {
            return accountMapper.toStaffRes(staffRepository.save(staff));
        } catch (AppException e) {
            throw new AppException(ErrorCode.PROCESS_FAILED);
        }
    }

    public void promoteToManager(String code, PromoteStaffRequest request){
        Staff staff = staffRepository.findStaffByCode(code);
        if (staff == null || !staff.isActive())
            throw new AppException(ErrorCode.STAFF_NOT_FOUND);
        Salon salon = salonRepository.findById(request.getSalonId()).orElseThrow(() -> new AppException(ErrorCode.SALON_NOT_FOUND));
        staff.setSalons(salon);
        staff.setRole(Role.MANAGER);
        Account account = staff.getAccount();
        if (account != null) {
            if (account.getRole().equals(Role.STAFF) || account.getRole().equals(Role.STYLIST)) {
                account.setRole(Role.MANAGER);
            }
        }
        managerService.createManager(staff, salon);
    }

    public String generateStaffCode() {
        String code = "S0001";
        String latestCode = staffRepository.findTheLatestStaffCode();
        if (latestCode == null)
            return code;
        int fourLastChar = Integer.parseInt(latestCode.substring(1));
        code = String.format("S%04d", ++fourLastChar);
        return code;
    }
}
