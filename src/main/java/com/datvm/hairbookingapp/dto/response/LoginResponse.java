package com.datvm.hairbookingapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
