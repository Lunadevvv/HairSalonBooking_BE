package com.datvm.hairbookingapp.dto.request;

import com.datvm.hairbookingapp.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
//    private String code;
    @Size(min = 2, message = "First name must be at least 2 characters")
    private String firstName;

    @Size(min = 2, message = "Last name must be at least 2 characters")
    private String lastName;

    @Email(message = "Invalid email")
    String email;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})\\b" , message = "Invalid phone number")
    String phone;

    @Size(min = 6 , message = "Password must be exceed 6 characters ")
    String password;

    private int shinePoint;

    Role role;
}
