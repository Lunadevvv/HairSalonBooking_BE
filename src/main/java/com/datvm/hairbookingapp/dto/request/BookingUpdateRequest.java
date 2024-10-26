package com.datvm.hairbookingapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingUpdateRequest {
    @NotBlank(message = "bookingId can't be blank")
    String bookingId;
    @NotNull(message = "slotId can't be null")
    Long slotId;
    @NotNull(message = "date can't be null")
    LocalDate date;
}
