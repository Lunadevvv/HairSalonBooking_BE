package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.BookingRequest;
import com.datvm.hairbookingapp.dto.request.BookingUpdateRequest;
import com.datvm.hairbookingapp.dto.response.BookingResponse;
import com.datvm.hairbookingapp.dto.response.FeedbackResponse;
import com.datvm.hairbookingapp.dto.response.PaymentResponse;
import com.datvm.hairbookingapp.dto.response.ServicesResponse;
import com.datvm.hairbookingapp.dto.response.EmailDetail;
import com.datvm.hairbookingapp.entity.*;
import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import com.datvm.hairbookingapp.entity.enums.FeedbackStatus;
import com.datvm.hairbookingapp.entity.enums.Role;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.BookingMapper;
import com.datvm.hairbookingapp.mapper.FeedbackMapper;
import com.datvm.hairbookingapp.mapper.PaymentMapper;
import com.datvm.hairbookingapp.mapper.ServicesMapper;
import com.datvm.hairbookingapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@EnableScheduling
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    BookingMapper bookingMapper;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AuthenticationRepository accountRepository;
    @Autowired
    private ServicesMapper servicesMapper;
    @Autowired
    SalonRepository salonRepository;
    @Autowired
    PaymentMapper paymentMapper;
    @Autowired
    FeedbackMapper feedbackMapper;

    @Autowired
    EmailService emailService;

    public void updateBookingStatus(String id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.BOOKING_NOT_FOUND)
        );
        if(booking.getStatus()==BookingStatus.SUCCESS){
            LocalDateTime date = LocalDateTime.now();
            booking.getPayment().setDate(date);
        }
        booking.setStatus(status);
        bookingRepository.save(booking);
    }

    public BookingResponse submitBooking(BookingRequest request) {
        Account account = authenticationService.getCurrentAccount();
        BookingResponse res = new BookingResponse();
        if (bookingRepository.countBookingInSlot(request.getDate(), request.getSlotId()) == staffRepository.countStylist(Role.STYLIST)) {
            throw new AppException(ErrorCode.BOOKING_FULL);
        }
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
        Salon salon = salonRepository.findById(request.getSalonId()).orElseThrow(() -> new AppException(ErrorCode.SALON_NOT_FOUND));
        Staff staff = null;

        // Check if stylistId is "None"
        if ("None".equals(request.getStylistId())) {
            staff = getRandomAvailableStylist(request.getSlotId(), request.getDate(), salon);
        } else {
            staff = staffRepository.findStaffByCode(request.getStylistId());
            if(!staff.isStatus())
                throw new AppException(ErrorCode.STAFF_NOT_ACTIVE);
            Role role = staff.getRole();
            if (role != Role.STYLIST) {
                throw new AppException(ErrorCode.STYLIST_ONLY);
            }
        }

        List<Services> list = new ArrayList<>();
        for (String serviceId : request.getServiceId()) {
            var service = servicesRepository.findById(serviceId).orElseThrow(() -> new AppException(ErrorCode.SERVICES_NOT_EXISTED));
            if (!service.isStatus())
                throw new AppException(ErrorCode.SERVICES_NOT_ACTIVE);
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
        booking.setSalonId(request.getSalonId());
        Payment payment = new Payment();
        payment.setId(generatePaymentId());
        payment.setPrice(request.getPrice());
        payment.setBooking(booking);
        Feedback feedback = new Feedback();
        feedback.setId(generateFeedbackId());
        feedback.setStatus(FeedbackStatus.CLOSE);
        feedback.setBooking(booking);
        booking.setFeedback(feedback);
        booking.setPayment(payment);
        booking = bookingRepository.save(booking);

        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setAccount(account);//set receiver
        emailDetail.setSubject("Đặt lịch thành công!");
        emailDetail.setContent("Đặt lịch thành công vào lúc " + slot.getTimeStart() + " - " + booking.getDate().toString() + ". Cảm ơn bạn đã lựa chọn và tin tưởng 360Shine!");
        emailDetail.setLink("http://localhost:3000/");
        emailService.sendEmail(emailDetail);
        return BookingResponse.builder()
                .stylistId(staff.getCode())
                .services(list)
                .date(booking.getDate())
                .id(booking.getId())
                .price(booking.getPrice())
                .period(booking.getPeriod())
                .slot(booking.getSlot())
                .status(booking.getStatus())
                .salonId(booking.getSalonId())
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
                .salonId(booking.getSalonId())
                .build();
    }

    public String cancelBooking(String id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        if(booking.getStatus()==BookingStatus.RECEIVED)
            booking.setStatus(BookingStatus.CANCELED);
        else
            throw new AppException(ErrorCode.BOOKING_INVALID_CANCELLED);
        bookingRepository.save(booking);
        return "Booking has been cancelled";
    }

    public List<Booking> getBookingsBySalon() {
        Account account = authenticationService.getCurrentAccount();
        Staff staff = staffRepository.findStaffByAccount(account);
        List<Booking> bookings = bookingRepository.findBySalon(staff.getSalons().getId());
        return bookings;
    }

    public List<Booking> getBookingsBySalonAndStylist() {
        Account account = authenticationService.getCurrentAccount();
        Staff staff = staffRepository.findStaffByAccount(account);
        List<Booking> bookings = bookingRepository.findBySalonAndStylist(staff.getSalons().getId(), staff);
        return bookings;
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
    public void autoCancelBookings() {
        LocalTime time = LocalTime.of(LocalTime.now().getHour(),0, 0);
        Long slotId = slotRepository.findByTimeStart(time);
        System.out.println(slotId);
        bookingRepository.updateBySpecificTime(BookingStatus.CANCELED,slotId,BookingStatus.RECEIVED);
        System.out.println("THE SYSTEM HAS CANCELLED RECEIVED BOOKING AT SLOT "+slotId);
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
                .salonId(booking.getSalonId())
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

    public String generateFeedbackId() {
        String id = "F0001";
        String lastId = feedbackRepository.findLastId();
        if (lastId == null)
            return id;
        int fourLastNumber = Integer.parseInt(lastId.substring(1));
        id = String.format("F%04d", ++fourLastNumber);
        return id;
    }

    public String generatePaymentId() {
        String id = "P0001";
        String lastId = paymentRepository.findLastId();
        if (lastId == null)
            return id;
        int fourLastNumber = Integer.parseInt(lastId.substring(1));
        id = String.format("P%04d", ++fourLastNumber);
        return id;
    }

    private Staff getRandomAvailableStylist(Long slotId, LocalDate date, Salon salon) {
        List<Staff> availableStylists = staffRepository.findAvailableStylists(slotId, date, Role.STYLIST, salon);
        if (availableStylists.isEmpty()) {
            throw new AppException(ErrorCode.NO_AVAILABLE_STYLISTS);
        }
        Random random = new Random();
        return availableStylists.get(random.nextInt(availableStylists.size()));
    }

    public PaymentResponse findPaymentByBookingId(String id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        return paymentMapper.toPaymentResponse(booking.getPayment());
    }
    public FeedbackResponse findFeedbackByBookingId(String id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        return feedbackMapper.toFeedbackResponse(booking.getFeedback());
    }
    public List<ServicesResponse> findAllActiveService() {
        return servicesRepository.findAllActiveServices(true).stream().map(servicesMapper::toServicesResponse).toList();


    public String getFeedbackId(String bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        return booking.getFeedback().getId();
    }
    public String getPaymentId(String bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        return booking.getPayment().getId();

    }
}
