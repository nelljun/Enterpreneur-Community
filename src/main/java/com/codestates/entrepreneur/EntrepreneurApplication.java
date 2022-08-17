package com.codestates.entrepreneur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EntrepreneurApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntrepreneurApplication.class, args);
	}

}
