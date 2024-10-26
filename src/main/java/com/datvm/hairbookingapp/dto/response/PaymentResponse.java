package com.datvm.hairbookingapp.dto.response;

import com.datvm.hairbookingapp.entity.Booking;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private String id;
    private int price;
    private LocalDateTime date;
    private Booking booking;
}
