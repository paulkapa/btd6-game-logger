package com.paulkapa.btd6gamelogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * <h3>Main Class</h3>
 *
 * Spring Boot Application starter.
 *
 * @SpringBootApplication
 */
@SpringBootApplication
public class Btd6GameLoggerApplication {

	private static final Logger logger = LoggerFactory.getLogger(Btd6GameLoggerApplication.class);

	@Autowired
	public Btd6GameLoggerApplication() {}

	public static void main(String[] args) {
		Thread t = new Thread(() -> {
			for(Double i = 0.347d; i < 5d; i += 1.001d) {
				logger.warn(String.format("... %6.2f %% ...", (i*23 >= 100) ? 100d : i*23d));
			}
			logger.info("Aplication shutdown completed. You may close any remaining console windows opened by this application!");
		});
		Runtime.getRuntime().addShutdownHook(t);
		new SpringApplicationBuilder(Btd6GameLoggerApplication.class).run(args);
	}
}
