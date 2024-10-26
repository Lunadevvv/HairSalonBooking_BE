package com.datvm.hairbookingapp.service;


import com.datvm.hairbookingapp.dto.request.SalonCreationRequest;
import com.datvm.hairbookingapp.dto.request.SalonUpdateRequest;
import com.datvm.hairbookingapp.dto.response.SalonResponse;
import com.datvm.hairbookingapp.entity.Salon;
import com.datvm.hairbookingapp.entity.Staff;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.SalonMapper;
import com.datvm.hairbookingapp.repository.ManagerRepository;
import com.datvm.hairbookingapp.repository.SalonRepository;
import com.datvm.hairbookingapp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SalonService {
    @Autowired
    SalonRepository salonRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    SalonMapper salonMapper;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private ManagerRepository managerRepository;

    public SalonResponse CreateSalon(SalonCreationRequest request) {
        Salon salon = salonMapper.toSalon(request);
        salon.setId(generateSalonCode());
        salon.setOpen(true);
        return salonMapper.toSalonResponse(salonRepository.save(salon));
    }

    public SalonResponse findById(String id) {
        Salon salon = salonRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SALON_NOT_FOUND));
        return salonMapper.toSalonResponse(salon);
    }

    public List<SalonResponse> getAllSalons() {
        return salonRepository.findAll().stream().map(salonMapper::toSalonResponse).toList();
    }

    public SalonResponse UpdateSalon(SalonUpdateRequest request, String code) {
        Salon salon =  salonRepository.findById(code).orElseThrow(() -> new AppException(ErrorCode.SALON_NOT_FOUND));
        salon = salonMapper.updateSalon(request);
        return salonMapper.toSalonResponse(salonRepository.save(salon));
    }

    public String deleteSalon(String id) {
        Salon salon = salonRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SALON_NOT_FOUND));
        if(salon.getManager() != null) {
            salon.getManager().setSalon(null);
        }
        List<Staff> staffList = salon.getStaff();
        if(!staffList.isEmpty()) {
            for(Staff staff : staffList) {
                staff.setSalons(null);
            }
        }
        salon.setOpen(false);
        salonRepository.save(salon);
        return "Salon has been deleted";
    }

    public String generateSalonCode(){
        String code = "SA0001";
        String latestCode = salonRepository.findLastId();
        if(latestCode == null)
            return code;
        int fourLastChar = Integer.parseInt(latestCode.substring(2));
        code = String.format("SA%04d", ++fourLastChar);
        return code;
    }
}
