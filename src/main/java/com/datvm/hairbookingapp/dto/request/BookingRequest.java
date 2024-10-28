package com.datvm.hairbookingapp.dto.request;

import com.datvm.hairbookingapp.entity.Feedback;
import com.datvm.hairbookingapp.entity.Slot;
import com.datvm.hairbookingapp.entity.Staff;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {
    private String salonId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String stylistId; //"None"

    private Long slotId;

    private int price;

    private List<String> serviceId;

    private int period;
    private Feedback feedback;
}
