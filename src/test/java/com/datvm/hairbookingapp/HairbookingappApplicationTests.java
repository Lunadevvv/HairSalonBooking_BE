package com.datvm.hairbookingapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
class HairbookingappApplicationTests {

//	@Test
//	void contextLoads() {
//	}

	@Test
	void testDate(){

		LocalDate today = LocalDate.now();

		// Print today's date
		log.info("Today's date: " + today);
	}

}
