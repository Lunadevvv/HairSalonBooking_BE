package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.UpdateProfileRequest;
import com.datvm.hairbookingapp.dto.response.AccountResponse;
import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.AccountMapper;
import com.datvm.hairbookingapp.repository.AuthenticationRepository;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class ProfileService {

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AccountMapper accountMapper;

    public AccountResponse getMyProfile(){
        Account account = authenticationService.getCurrentAccount();
        return accountMapper.toAccountRes(account);
    }

    public AccountResponse updateMyProfile(UpdateProfileRequest request) throws SQLIntegrityConstraintViolationException{
        Account account = authenticationService.getCurrentAccount();
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setRole(request.getRole());
        account.setEmail(request.getEmail());
        account.setEmail(request.getEmail());
        return accountMapper.toAccountRes(authenticationRepository.save(account));
    }
}
