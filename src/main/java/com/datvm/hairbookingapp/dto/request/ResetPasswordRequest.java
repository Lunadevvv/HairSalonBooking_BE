package com.datvm.hairbookingapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @Size(min = 6 , message = "Password must be exceed 6 characters ")
    String newPassword;
}
