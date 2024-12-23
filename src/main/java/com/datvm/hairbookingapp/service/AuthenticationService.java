package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.ChangePasswordResquest;
import com.datvm.hairbookingapp.dto.request.LoginRequest;
import com.datvm.hairbookingapp.dto.request.RegisterRequest;
import com.datvm.hairbookingapp.dto.request.ResetPasswordRequest;
import com.datvm.hairbookingapp.dto.response.AccountResponse;
import com.datvm.hairbookingapp.dto.response.EmailDetail;
import com.datvm.hairbookingapp.dto.response.LoginResponse;
import com.datvm.hairbookingapp.dto.response.RegisterResponse;
import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.entity.enums.Role;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.mapper.AccountMapper;
import com.datvm.hairbookingapp.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    EmailService emailService;

    public RegisterResponse createAccount(RegisterRequest request){
        Account account = accountMapper.toAccount(request);
        try{
            if(request.getRole() == null)
                account.setRole(Role.MEMBER);
            else
                account.setRole(request.getRole());
            account.setPassword(passwordEncoder.encode(request.getPassword()));
            account = accountRepository.save(account);
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setAccount(account);//set receiver
            emailDetail.setSubject("Reset password");
            emailDetail.setContent("Đăng kí tài khoảng thành công. Cảm ơn bạn đã lựa chọn và tin tưởng 360Shine!");
            emailDetail.setLink("http://localhost:3000/");
            emailService.sendEmail(emailDetail);
            return accountMapper.toAuthRes(account);
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

    public String forgotPassword(String phone){
        Account account = accountRepository.findAccountByPhone(phone);
        if(account == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        String token = tokenProvider.generateToken(account);
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setAccount(account);//set receiver
        emailDetail.setSubject("Reset password");
        emailDetail.setContent("Bạn hãy nhấp vào ô bên dưới để reset mật khẩu!");
        emailDetail.setLink("http://localhost:3000/reset-password?token=" + token);
        emailService.sendEmail(emailDetail);
        return token;
    }

    public boolean resetPassword(ResetPasswordRequest request){
        boolean status = false;
        Account account = getCurrentAccount();
        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        try{
            accountRepository.save(account);
            status = true;
        }catch (AppException e){
            throw new AppException(ErrorCode.PROCESS_FAILED);

        }
        return status;
    }

    public AccountResponse changePassword(ChangePasswordResquest request){
        Account account = getCurrentAccount();
        if (!passwordEncoder.matches(request.getOldPassword(), account.getPassword()))
            throw new AppException(ErrorCode.PASSWORD_WRONG);
        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        try{
            return accountMapper.toAccountRes(accountRepository.save(account));
        }catch (AppException e){
            throw new AppException(ErrorCode.PROCESS_FAILED);
        }

    }

    public AccountResponse getAccountByPhone(String phone) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByPhone(phone);
        if (account == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return accountMapper.toAccountRes(account);
    }


    public Account getCurrentAccount(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findAccountByPhone(account.getPhone());
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
