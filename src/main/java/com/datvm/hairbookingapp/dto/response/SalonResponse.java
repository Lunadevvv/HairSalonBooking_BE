package com.datvm.hairbookingapp.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SalonResponse {
    Long salonId;
    String salonName;
    String salonAddress;
    String phoneNumber;
    String openingHours;
    String description;
}
