package com.datvm.hairbookingapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Payment {
    @Id
    private String id;
    private int price;
    private LocalDateTime date;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookingId")
    private Booking booking;
}
