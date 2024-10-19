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

    @ManyToOne
    @JoinColumn(name = "stylist_id", nullable = false)
    private Staff stylistId; // Consider renaming to 'staff' for clarity

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private Slot slot;

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

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int period;
}
