package com.datvm.hairbookingapp.dto.response;

import com.datvm.hairbookingapp.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int shinePoint;
    private Role role;
}
