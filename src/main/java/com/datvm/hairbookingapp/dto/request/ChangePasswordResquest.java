package com.datvm.hairbookingapp.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordResquest {
    @Size(min = 6 , message = "Password must be exceed 6 characters ")
    private String oldPassword;

    @Size(min = 6 , message = "Password must be exceed 6 characters ")
    private String newPassword;
}
