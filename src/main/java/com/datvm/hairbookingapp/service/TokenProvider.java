package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.repository.AuthenticationRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenProvider {
    @Value("${app.jwtSecret}")
    private String secretKey;

    @Autowired
    AuthenticationRepository authenticationRepository;

    //Tao token
    public String generateToken(Account account){
        String token = Jwts.builder()
                .subject(account.getPhone()) //thong tin dac biet cua user (ko trung nhau dc)
                .issuedAt(new Date(System.currentTimeMillis())) //thoi gian tao
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) //1 tieng
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        return token;
    }

    //Verify token
    public Account getAccountByToken(String token){
        //nho jwt decode token de lay thong tin
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build().parseSignedClaims(token)
                .getPayload(); //lay noi dung cua token

        String subject = claims.getSubject();
        Account account = authenticationRepository.findAccountByPhone(subject);
        return account;
    }
}
