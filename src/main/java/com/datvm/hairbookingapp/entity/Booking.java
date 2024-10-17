package com.datvm.hairbookingapp.entity;

import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @Id
    private String id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String stylistId;

    @Column(nullable = false)
    private int slotId;

    @Column(nullable = false)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToMany
    @JoinTable(
            name = "booking_services", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "booking_id"), // Khóa ngoại cho Booking
            inverseJoinColumns = @JoinColumn(name = "service_id") // Khóa ngoại cho Services
    )
    private List<Services> services;
}
