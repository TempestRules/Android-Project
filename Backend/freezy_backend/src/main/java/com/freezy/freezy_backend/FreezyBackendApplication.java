package com.freezy.freezy_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FreezyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreezyBackendApplication.class, args);
	}

	//Test method
	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {

		};
	}

}
