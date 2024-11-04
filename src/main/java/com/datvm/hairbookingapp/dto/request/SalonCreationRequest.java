package com.datvm.hairbookingapp.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class SalonCreationRequest {
//    private boolean open;
    @NotBlank(message = "address can't be blank")
    private String address;
    @NotBlank(message = "district can't be blank")
    private String district;
    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})\\b" , message = "Invalid phone number")
    private String hotline;
    @Pattern(regexp = "https://imgur\\.com.*", message = "Invalid url. Default: https://imgur.com")
    private String image;
}
