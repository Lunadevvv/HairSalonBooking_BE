package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.AuthenticationRequest;
import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.mapper.AccountMapper;
import com.datvm.hairbookingapp.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    public Account createAccount(AuthenticationRequest request){
        if(accountRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException();

        Account account = accountMapper.toAccount(request);
        return accountRepository.save(account);
    }
}
