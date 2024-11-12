package com.datvm.hairbookingapp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PromoteStaffRequest {
    @NotNull(message = "Hãy chọn salon!")
    private String salonId;
}
