package com.coworking.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaCoworkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaCoworkingApplication.class, args);
	}

}
