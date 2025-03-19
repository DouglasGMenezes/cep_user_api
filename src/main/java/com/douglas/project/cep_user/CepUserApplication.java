package com.douglas.project.cep_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CepUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(CepUserApplication.class, args);
	}

}
