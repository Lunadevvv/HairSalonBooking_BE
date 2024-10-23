package com.datvm.hairbookingapp.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SalonUpdateRequest {
    @NotNull(message = "salonId is required")
    private String id;
    private boolean open;
    @NotBlank(message = "address can't be blank")
    private String address;
    @NotBlank(message = "district can't be blank")
    private String district;
}
