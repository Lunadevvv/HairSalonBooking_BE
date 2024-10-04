package com.datvm.hairbookingapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
