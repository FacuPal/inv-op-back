package com.inv.op.backend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// import com.inv.op.backend.util.StringValidator;

@SpringBootApplication
public class BackendApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	// @Bean
	// public StringValidator stringValidator() {
	// 	return new StringValidator();
	// }
	
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}

