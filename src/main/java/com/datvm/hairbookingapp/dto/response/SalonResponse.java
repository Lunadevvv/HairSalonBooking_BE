package com.datvm.hairbookingapp.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
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
