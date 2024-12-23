package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.CreateStaffRequest;
import com.datvm.hairbookingapp.dto.request.PromoteStaffRequest;
import com.datvm.hairbookingapp.dto.response.ManagerResponse;
import com.datvm.hairbookingapp.entity.Manager;
import com.datvm.hairbookingapp.entity.Salon;
import com.datvm.hairbookingapp.entity.Staff;
import com.datvm.hairbookingapp.entity.enums.Role;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.ManagerMapper;
import com.datvm.hairbookingapp.repository.ManagerRepository;
import com.datvm.hairbookingapp.repository.SalonRepository;
import com.datvm.hairbookingapp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private SalonRepository salonRepository;

    public void createManager(Staff staff, Salon salon) {
        Manager manager = new Manager();
        manager.setStaff(staff);
        manager.setId(generateManagerId());
        manager.setSalon(salon);
        managerRepository.save(manager);
        salon.setManager(manager);
        salonRepository.save(salon);
    }

    public ManagerResponse getManagerById(String id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MANAGER_NOT_FOUND));
        return managerMapper.toManagerResponse(manager);
    }

    public List<ManagerResponse> getAllManagers() {
        List<Manager> managers = managerRepository.findAll();
        return managers.stream().map(managerMapper::toManagerResponse).toList();
    }
    //        // có salonId trong request va manager chua quan li salon nao
//        if (request.getSalonId() != null && manager.getSalon() == null) {
//            String code = managerRepository.findStaffCodeByManagerId(id);
//            staffService.updateStaffProfile(request, code);
//            manager.setSalon(salon);
//            manager.getSalon().setManager(manager);
//            return managerMapper.toManagerResponse(managerRepository.save(manager));
//            // salonId cũ trong request va manager da quan li salon
//        } else if (request.getSalonId() == manager.getSalon().getId() && manager.getSalon() != null) {
//            String code = managerRepository.findStaffCodeByManagerId(id);
//            staffService.updateStaffProfile(request, code);
//            return managerMapper.toManagerResponse(managerRepository.save(manager));
//            //truong hop con lai
//        } else
//            throw new AppException(ErrorCode.INVALID_ACTION);
    public void updateManagerInfo(PromoteStaffRequest request, String id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MANAGER_NOT_FOUND));
        if(request.getSalonId().equals("None") ){
            manager.getStaff().setSalons(null);
            manager.getSalon().setManager(null);
            salonRepository.save(manager.getSalon());
            staffRepository.save(manager.getStaff());
            manager.setSalon(null);
            managerRepository.save(manager);
            return;
        }

        Salon salon = salonRepository.findById(request.getSalonId()).orElseThrow(() -> new AppException(ErrorCode.SALON_NOT_FOUND));
        Staff staff = manager.getStaff();

        if (!salon.isOpen()) {
            throw new AppException(ErrorCode.SALON_CLOSED);
        } else if (!(salon.getManager() == null)){//Ktra salon muon doi qua da co manager chua
            throw new AppException(ErrorCode.INVALID_ACTION);
        }
        salon.setManager(manager);
        staff.setSalons(salon);
        manager.setSalon(salon);
        salonRepository.save(salon);
        staffRepository.save(staff);
        managerRepository.save(manager);
    }

    //kick manager
    public String deleteManager(String id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MANAGER_NOT_FOUND));
        Staff staff = manager.getStaff();
        staffService.demoteManager(staff);
        manager.getSalon().setManager(null);
        managerRepository.delete(manager);
        return "Manager has been deleted";
    }

    //deGrade manager
//    public String deGradeManager(String id) {
//        Manager manager = managerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MANAGER_NOT_FOUND));
//        Staff staff = manager.getStaff();
//        staff.setRole(Role.STAFF);
//        staff.setManager(null);
//        staff.getAccount().setRole(staff.getRole());
//        manager.getSalon().setManager(null);
//        managerRepository.delete(manager);
//        return "Manager has been degraded to staff";
//    }

    //remove management
//    public String removeManage(String id) {
//        Manager manager = managerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MANAGER_NOT_FOUND));
//        if(manager.getSalon() != null) {
//            manager.getSalon().setManager(null);
//            manager.setSalon(null);
//        }else
//            return "this manager already manage no salon";
//        managerRepository.save(manager);
//        return "manager now manage no salon";
//    }


    public String generateManagerId() {
        String code = "M0001";
        String latestCode = managerRepository.findLastId();
        if (latestCode == null)
            return code;
        int fourLastChar = Integer.parseInt(latestCode.substring(1));
        code = String.format("M%04d", ++fourLastChar);
        return code;
    }
}
