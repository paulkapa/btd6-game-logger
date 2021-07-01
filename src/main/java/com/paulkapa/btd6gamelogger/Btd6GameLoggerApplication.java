package com.paulkapa.btd6gamelogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Btd6GameLoggerApplication {

	private static SpringApplication appHandle;
	private static ConfigurableApplicationContext  context;

	public static void main(String[] args) {
		appHandle = new SpringApplication(Btd6GameLoggerApplication.class);
		context = appHandle.run(args);
	}

	public static void shutdownApp(int returnCode) {
		SpringApplication.exit(context, () -> returnCode);
	}

}
