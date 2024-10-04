package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.AuthenticationRequest;
import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@Slf4j
@SpringBootTest //Init framework (can thiet cho viec test 1 application Spring)
@AutoConfigureMockMvc //tao 1 mock request trong unit test den controller
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    //Do khi chay kiem thu api se ko di xuong tang service. Nen phai tao 1 mockbean tang service
    @MockBean
    AuthenticationService authenticationService;

    private AuthenticationRequest request;
    private Account account;
    private LocalDate dob;

    @BeforeEach //Ham nay se dc goi truoc moi lan testDD
    public void initData(){
        dob = LocalDate.of(1999, 1,1);

        request = AuthenticationRequest.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .password("1234567")
                .yob(dob)
                .build();

        account = Account.builder()
                .id(Long.valueOf(3))
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .yob(dob)
                .build();
    }

    @Test
    void createAccount_validRequest_success() throws Exception {
        //Given: Nhung du lieu dau vao
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request); //Serialize object thanh chuoi string

        Mockito.when(authenticationService.createAccount(ArgumentMatchers.any()))
                        .thenReturn(account);
        //When: Khi nao request api can test
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.username").value("john")
                );
        //Then: Khi then xay ra thi minh expect gi
    }

    @Test
    void createAccount_usernameInvalid_fail() throws Exception {
        //Given: Nhung du lieu dau vao
        request.setUsername("joh");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request); //Serialize object thanh chuoi string

        //When: Khi nao request api can test
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk()
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value("0"))
//                .andExpect(MockMvcResultMatchers.jsonPath("message").value("At least 4 characters")
                );
        //Then: Khi then xay ra thi minh expect gi
    }
}
