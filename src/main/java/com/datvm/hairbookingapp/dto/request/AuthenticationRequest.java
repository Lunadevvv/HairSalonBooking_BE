package com.datvm.hairbookingapp.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
//    private String code;
    @Size(min = 4, message = "At least 4 characters")
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate yob;
}
