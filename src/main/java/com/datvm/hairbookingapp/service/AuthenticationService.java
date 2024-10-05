package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.LoginRequest;
import com.datvm.hairbookingapp.dto.request.RegisterRequest;
import com.datvm.hairbookingapp.dto.response.LoginResponse;
import com.datvm.hairbookingapp.dto.response.RegisterResponse;
import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.entity.Role;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.AccountMapper;
import com.datvm.hairbookingapp.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private AuthenticationRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenProvider tokenProvider;

    public RegisterResponse createAccount(RegisterRequest request){
        Account account = accountMapper.toAccount(request);
        try{
            if(request.getRole() == null)
                account.setRole(Role.MEMBER);
            else
                account.setRole(request.getRole());
            account.setPassword(passwordEncoder.encode(request.getPassword()));
            return accountMapper.toAuthRes(accountRepository.save(account));
        }catch(Exception e){
            if(e.getMessage().contains(account.getEmail())){
                throw new AppException(ErrorCode.DUPLICATE_EMAIL);
            }else{
                throw new AppException(ErrorCode.DUPLICATE_PHONE);
            }
        }
    }

    public LoginResponse login(LoginRequest request){
        try{
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(// xac thuc
                            // username , password (
                            // tu dong ma hoa password user va check tren database )
                        request.getUsername(), request.getPassword() // go to loadUserByUsername(String phone)
                            // to check username in db first -> so sanh password db with request password
                    ));

            //Nếu account exists
            Account account = (Account) authentication.getPrincipal();//tra ve acount tu db
            LoginResponse loginResponse = accountMapper.toLoginRes(account);
            loginResponse.setToken(tokenProvider.generateToken(account));
            return loginResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByPhone(phone);
        if (account == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return account;
    }
}
