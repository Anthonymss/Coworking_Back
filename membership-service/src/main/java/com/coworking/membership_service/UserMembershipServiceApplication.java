package com.coworking.membership_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserMembershipServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMembershipServiceApplication.class, args);
	}

}
