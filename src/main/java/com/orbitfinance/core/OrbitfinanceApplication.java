package com.orbitfinance.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class OrbitfinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrbitfinanceApplication.class, args);
	}

}
