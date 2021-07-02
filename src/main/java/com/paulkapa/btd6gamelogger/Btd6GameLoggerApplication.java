package com.paulkapa.btd6gamelogger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Spring Application Starter
 */
@SpringBootApplication
public class Btd6GameLoggerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Btd6GameLoggerApplication.class).run();
	}
}
