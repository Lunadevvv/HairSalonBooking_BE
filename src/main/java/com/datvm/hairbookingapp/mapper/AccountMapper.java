package com.datvm.hairbookingapp.mapper;

import com.datvm.hairbookingapp.dto.request.AuthenticationRequest;
import com.datvm.hairbookingapp.dto.response.AuthenticationResponse;
import com.datvm.hairbookingapp.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toAccount(AuthenticationRequest request);
    AuthenticationResponse toAuthRes(Account account);
}
