package com.thirdware.ChatBotService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages={"com.thirdware.*","com.thirdware.repositories","com.thirdware.controllers","com.thirdware.services","com.thirdware.cors","com.thirdware.vo","com.thidware.uitls"})
@EnableJpaRepositories(basePackages="com.thirdware.repositories")
@EntityScan(basePackages="com.thirdware.entity")
@SpringBootApplication
public class ChatBotServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatBotServiceApplication.class, args);
	}

}
