package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.mapper.BookingMapper;
import com.datvm.hairbookingapp.repository.BookingRepository;
import com.datvm.hairbookingapp.repository.ServicesRepository;
import com.datvm.hairbookingapp.repository.SlotRepository;
import com.datvm.hairbookingapp.repository.StaffRepository;
import com.datvm.hairbookingapp.service.AuthenticationService;
import com.datvm.hairbookingapp.service.BookingService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static io.restassured.RestAssured.given;

@Slf4j
@SpringBootTest //Init framework (can thiet cho viec test 1 application Spring)
@AutoConfigureMockMvc //tao 1 mock request trong unit test den controller
public class BookingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    SlotRepository slotRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ServicesRepository servicesRepository;

    @Autowired
    BookingMapper bookingMapper;

    private static String token;

    @BeforeAll
    public static void setUp() {
        // Set the base URI for your API
        RestAssured.baseURI = "http://localhost:8080";
        String requestBody = """
                {
                    "username": "0357968555",
                    "password": "123456"
                }
                """;

        // Retrieve the token by logging in (or another method your API uses for token generation)
        Response response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .post("/api/v1/auth/login"); // Assuming "/login" is your auth endpoint

        token = response.jsonPath().getString("result.token"); // Adjust to the actual path to the token in response

    }

    @Test
    void submitBooking_successCase() throws Exception {

        String requestBody = """
                {
                    "date": "2024-11-06",
                    "stylistId": "S0003",
                    "slotId": 1,
                    "price": 50000,
                    "serviceId" : ["SV0001"],
                    "period": 3
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/booking")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
        //Then: Khi then xay ra thi minh expect gi
    }

    @Test
    void submitBooking_failCase() throws Exception {

        String requestBody = """
                {
                    "date": "2024-11-06",
                    "stylistId": "S0002",
                    "slotId": 1,
                    "price": 50000,
                    "serviceId" : ["SV0001"],
                    "period": 3
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/booking")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
        //Then: Khi then xay ra thi minh expect gi
    }
}
