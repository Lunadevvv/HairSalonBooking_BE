package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.BookingRequest;
import com.datvm.hairbookingapp.dto.request.BookingUpdateRequest;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@EnableScheduling
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

    public void updateBookingStatus(String id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.BOOKING_NOT_FOUND)
        );
        booking.setStatus(status);
        bookingRepository.save(booking);
    }

    public BookingResponse submitBooking(BookingRequest request) {
        Account account = authenticationService.getCurrentAccount();
        BookingResponse res = new BookingResponse();
        //check the latest booking status is CANCELED or COMPLETED
        var lastBooking = bookingRepository.findLastBooking(account);
        if (lastBooking != null) {
            if (lastBooking.getStatus() == BookingStatus.CANCELED || lastBooking.getStatus() == BookingStatus.COMPLETED) {
                //Generate new Booking
                res = createBooking(request, account);
            } else {
                throw new AppException(ErrorCode.CANT_SUBMIT_BOOKING);
            }
        } else
            res = createBooking(request, account);
        return res;
    }

    public BookingResponse createBooking(BookingRequest request, Account account) {
        String id = generateBookingId();
        Slot slot = slotRepository.findById(request.getSlotId()).orElseThrow(() -> new AppException(ErrorCode.EMPTY_SLOT));

        Staff staff = null;

        // Check if stylistId is "None"
        if ("None".equals(request.getStylistId())) {
            staff = getRandomAvailableStylist(request.getSlotId(), request.getDate());
        } else {
            staff = staffRepository.findStaffByCode(request.getStylistId());
            Role role = staff.getRole();
            if (role != Role.STYLIST) {
                throw new AppException(ErrorCode.STYLIST_ONLY);
            }
        }

        List<Services> list = new ArrayList<>();
        for (String serviceId : request.getServiceId()) {
            var service = servicesRepository.findById(serviceId).orElseThrow(() -> new AppException(ErrorCode.SERVICES_NOT_EXISTED));
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
        booking.setPeriod(request.getPeriod());

        booking = bookingRepository.save(booking);
        return BookingResponse.builder()
                .stylistId(staff.getCode())
                .services(list)
                .date(booking.getDate())
                .id(booking.getId())
                .price(booking.getPrice())
                .period(booking.getPeriod())
                .slot(booking.getSlot())
                .status(booking.getStatus())
                .build();
    }

    public BookingResponse updateBookingSlot(BookingUpdateRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        Slot slot = slotRepository.findById(request.getSlotId()).orElseThrow(() -> new AppException(ErrorCode.EMPTY_SLOT));
        if (bookingRepository.countBookingInSlot(request.getDate(), request.getSlotId()) < staffRepository.countStylist(Role.STYLIST)) {
            booking.setSlot(slot);
            booking.setDate(request.getDate());
            booking = bookingRepository.save(booking);
        } else
            throw new AppException(ErrorCode.BOOKING_FULL);
        return BookingResponse.builder()
                .stylistId(booking.getStylistId().getCode())
                .services(booking.getServices())
                .date(booking.getDate())
                .id(booking.getId())
                .price(booking.getPrice())
                .period(booking.getPeriod())
                .slot(booking.getSlot())
                .status(booking.getStatus())
                .build();
    }

    public String deleteBooking(String id) {
        if(!bookingRepository.existsById(id))
            throw new AppException(ErrorCode.BOOKING_NOT_FOUND);
        bookingRepository.deleteById(id);
        return "Booking has been deleted";
    }

    public List<Booking> getBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings;
    }

    public List<Booking> getBookingsByAccount(){
        Account account = authenticationService.getCurrentAccount();
        List<Booking> bookings = bookingRepository.findByAccount(account);
        return bookings;
    }

    @Scheduled(cron = "0 30 * * * ?")
    public void cancelBookings() {
        System.out.println("THE SYSTEM HAS TRIED TO DO THIS !!!!!!!!!!!!!!!!!!!");
        LocalTime time = LocalTime.of(LocalTime.now().getHour(),0, 0);
        Long slotId = slotRepository.findByTimeStart(time);
        System.out.println(slotId);
        bookingRepository.updateBySpecificTime(BookingStatus.CANCELED,slotId,BookingStatus.RECEIVED);
    }

    public BookingResponse cancelPeriodBooking(String id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        booking.setPeriod(0);
        booking = bookingRepository.save(booking);

        return BookingResponse.builder()
                .stylistId(booking.getStylistId().getCode())
                .services(booking.getServices())
                .date(booking.getDate())
                .id(booking.getId())
                .price(booking.getPrice())
                .period(booking.getPeriod())
                .slot(booking.getSlot())
                .status(booking.getStatus())
                .build();
    }



    public String generateBookingId() {
        String id = "B0001";
        String lastId = bookingRepository.findLastId();
        if (lastId == null)
            return id;
        int fourLastNumber = Integer.parseInt(lastId.substring(1));
        id = String.format("B%04d", ++fourLastNumber);
        return id;
    }

    private Staff getRandomAvailableStylist(Long slotId, LocalDate date) {
        List<Staff> availableStylists = staffRepository.findAvailableStylists(slotId, date, Role.STYLIST);
        if (availableStylists.isEmpty()) {
            throw new AppException(ErrorCode.NO_AVAILABLE_STYLISTS);
        }
        Random random = new Random();
        return availableStylists.get(random.nextInt(availableStylists.size()));
    }
}
