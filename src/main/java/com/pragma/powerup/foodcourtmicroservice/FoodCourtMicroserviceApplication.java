package com.pragma.powerup.foodcourtmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients
//@EnableHystrix
//@EnableHystrixDashboard
public class FoodCourtMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodCourtMicroserviceApplication.class, args);
	}

}
