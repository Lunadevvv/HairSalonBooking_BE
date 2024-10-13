package com.datvm.hairbookingapp.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ForgotRequest {
    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})\\b" , message = "Invalid phone number")
    String phone;
}
