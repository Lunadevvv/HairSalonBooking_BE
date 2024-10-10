package com.datvm.hairbookingapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SalonCreationRequest {
    @NotNull(message = "salonId can't be null")
    Long salonId;

    @NotBlank(message = "salonName can't be blank")
    String salonName;

    @NotBlank(message = "salonAddress can't be blank")
    String salonAddress;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})\\b", message = "Invalid phone number")
    String phoneNumber;

    @NotBlank(message = "openingHours can't be blank")
    String openingHours;

    @NotBlank(message = "description can't be blank")
    String description;
}
