package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.dto.request.RegisterRequest;
import com.datvm.hairbookingapp.dto.response.RegisterResponse;
import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.repository.AuthenticationRepository;
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
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    AuthenticationRepository authenticationRepository;


    private RegisterRequest registerRequest_success;
    private RegisterRequest registerRequest_fail;
    private RegisterResponse account;

    @BeforeEach //Ham nay se dc goi truoc moi lan testDD
    public void initData(){

        registerRequest_success = RegisterRequest.builder()
                .phone("0919859856")
                .email("test8@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .password("1234567")
                .build();

        registerRequest_fail = RegisterRequest.builder()
                .phone("0357968555")
                .email("vuminhdat98@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .password("123456")
                .build();

        account = RegisterResponse.builder()
                .id(Long.valueOf(10))
                .phone("0919859856")
                .email("test8@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .build();
    }

    @Test
    void createAccount_validRequest_success() throws Exception {
        //Given: Nhung du lieu dau vao
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(registerRequest_success); //Serialize object thanh chuoi string

        //When: Khi nao request api can test
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
        //Then: Khi then xay ra thi minh expect gi
    }

    @Test
    void createAccount_duplicateAccount_fail() throws Exception {
        //Given: Nhung du lieu dau vao
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(registerRequest_fail); //Serialize object thanh chuoi string
        //When: Khi nao request api can test
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
        //Then: Khi then xay ra thi minh expect gi
    }

    @Test
    void login_successCase() throws Exception {

        String requestBody = """
                {
                    "username": "0357968555",
                    "password": "123456"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
        //Then: Khi then xay ra thi minh expect gi
    }

    @Test
    void login_failCase() throws Exception {

        String requestBody = """
                {
                    "username": "0357965555",
                    "password": "123456"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
        //Then: Khi then xay ra thi minh expect gi
    }
}
