package com.datvm.hairbookingapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingUpdateRequest {
    @NotBlank(message = "bookingId can't be blank")
    String bookingId;
    @NotNull(message = "slotId can't be null")
    Long slotId;
}
