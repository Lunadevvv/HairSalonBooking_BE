package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.BookingRequest;
import com.datvm.hairbookingapp.dto.response.BookingResponse;
import com.datvm.hairbookingapp.entity.*;
import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import com.datvm.hairbookingapp.entity.enums.Role;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.BookingMapper;
import com.datvm.hairbookingapp.repository.BookingRepository;
import com.datvm.hairbookingapp.repository.ServicesRepository;
import com.datvm.hairbookingapp.repository.SlotRepository;
import com.datvm.hairbookingapp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    SlotRepository slotRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ServicesRepository servicesRepository;

    @Autowired
    BookingMapper bookingMapper;

    public BookingResponse createBooking(BookingRequest request){
        Account account = authenticationService.getCurrentAccount();
        String id = generateBookingId();
        Slot slot = slotRepository.findById(request.getSlotId()).orElseThrow(() -> new AppException(ErrorCode.EMPTY_SLOT));
        Staff staff = staffRepository.findStaffByCode(request.getStylistId());
        Role role = staff.getRole();
        if(role != Role.STYLIST)
            throw new AppException(ErrorCode.STYLIST_ONLY);

        List<Services> list = new ArrayList<>();
        for (String serviceId : request.getServiceId()) {
            var service = servicesRepository.findByServiceId(serviceId).orElseThrow(() -> new AppException(ErrorCode.SERVICES_NOT_EXISTED));
            list.add(service);
        }

        Booking booking = new Booking();
        booking.setId(id);
        booking.setDate(request.getDate());
        booking.setSlot(slot);
        booking.setPrice(request.getPrice());
        booking.setStatus(BookingStatus.RECEIVED);
        booking.setStylistId(staff);
        booking.setAccount(account);
        booking.setServices(list);

        return bookingMapper.toRes(bookingRepository.save(booking));
    }

    public String generateBookingId() {
        String id = "B0001";
        String lastId = bookingRepository.findLastId();
        if(lastId == null)
            return id;
        int fourLastNumber = Integer.parseInt(lastId.substring(1));
        id = String.format("B%04d", ++fourLastNumber);
        return id;
    }
}
