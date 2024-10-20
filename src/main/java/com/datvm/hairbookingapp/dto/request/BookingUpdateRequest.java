package com.datvm.hairbookingapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookingUpdateRequest {
    @NotBlank(message = "categoryId can't be blank")
    String bookingId;
    @NotBlank(message = "categoryId can't be blank")
    String slotId;
}
