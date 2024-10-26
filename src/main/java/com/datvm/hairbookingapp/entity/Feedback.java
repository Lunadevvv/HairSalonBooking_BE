package com.datvm.hairbookingapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Feedback {
    @Id
    private String id;
    private String rate;
    private String feedback;
    @OneToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;
}
