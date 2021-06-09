package com.paulkapa.btd6gamelogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Btd6GameLoggerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Btd6GameLoggerApplication.class);
		application.setWebApplicationType(WebApplicationType.REACTIVE);
		application.run(args);
	}

}
