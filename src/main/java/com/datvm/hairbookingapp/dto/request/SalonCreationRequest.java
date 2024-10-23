package com.datvm.hairbookingapp.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class SalonCreationRequest {
    private boolean open;
    @NotBlank(message = "address can't be blank")
    private String address;
    @NotBlank(message = "district can't be blank")
    private String district;
}
