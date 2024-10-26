package com.datvm.hairbookingapp.dto.response;

import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.entity.Services;
import com.datvm.hairbookingapp.entity.Slot;
import com.datvm.hairbookingapp.entity.Staff;
import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import com.datvm.hairbookingapp.service.BookingService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private String id;

    private LocalDate date;

    private String stylistId;

    private int price;

    private BookingStatus status;

    private int period;

    private Slot slot;

//    private Account account;

    private List<Services> services;


}
