package com.datvm.hairbookingapp.dto.request;

import com.datvm.hairbookingapp.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class CreateStaffRequest {
    @NotNull(message = "First Name can't be null")
    private String firstName;

    @NotNull(message = "Last Name can't be null")
    private String lastName;

    @NotNull(message = "Gender can't be null")
    private String gender;

    @NotNull(message = "Yob can't be null")
    private int yob;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})\\b" , message = "Invalid phone number")
    private String phone;

    @Email(message = "Invalid email")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Join in can't be null")
    private LocalDate joinIn;

//    @NotNull(message = "Role can't be null")
    private Role role;

    @Pattern(regexp = "https://imgur\\.com.*", message = "Invalid url. Default: https://imgur.com")
    private String image;
}
