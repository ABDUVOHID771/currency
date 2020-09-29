package com.example.currencyeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CurrencyEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyEurekaApplication.class, args);
	}

}
