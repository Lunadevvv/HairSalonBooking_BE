package com.datvm.hairbookingapp.entity;

import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import com.datvm.hairbookingapp.entity.enums.FeedbackStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Feedback {
    @Id
    private String id;
    private String rate;
    private String feedback;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FeedbackStatus status;
    @OneToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;
}
