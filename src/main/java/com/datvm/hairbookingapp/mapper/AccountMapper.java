package com.datvm.hairbookingapp.mapper;

import com.datvm.hairbookingapp.dto.request.LoginRequest;
import com.datvm.hairbookingapp.dto.request.RegisterRequest;
import com.datvm.hairbookingapp.dto.request.UpdateProfileRequest;
import com.datvm.hairbookingapp.dto.response.AccountResponse;
import com.datvm.hairbookingapp.dto.response.LoginResponse;
import com.datvm.hairbookingapp.dto.response.RegisterResponse;
import com.datvm.hairbookingapp.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toAccount(RegisterRequest request);
    RegisterResponse toAuthRes(Account account);
    LoginResponse toLoginRes(LoginRequest request);
    LoginResponse toLoginRes(Account account);
    AccountResponse toAccountRes(Account account);
    Account fromUpdateProfileToAccount(UpdateProfileRequest updateProfileRequest);
}
