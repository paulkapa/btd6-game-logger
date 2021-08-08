package com.paulkapa.btd6gamelogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * <b>Main Class</b>
 * <p>
 * Spring Boot Application starter.
 */
@SpringBootApplication
public class Btd6GameLoggerApplication {

    private static Btd6GameLoggerApplication instance = null;
    private static SpringApplicationBuilder builder = null;
    private static String argsString = "null";

    /**
     * org.slf4j.Logger accessor.
     * <p>
     * Prints logs to the console in the same format as the default Spring
     * Application logs.
     */
    private static final Logger logger = LoggerFactory.getLogger(Btd6GameLoggerApplication.class);

    public static void main(String[] args) {
        if (instance == null) {
            instance = new Btd6GameLoggerApplication();
            addShutdownHook(args);
        }
        if (builder == null) {
            builder = new SpringApplicationBuilder(Btd6GameLoggerApplication.instance.getClass())
                    .web(WebApplicationType.SERVLET);
        }
        builder.run(args);
    }

    private static void addShutdownHook(String[] args) {
        if (args != null && args.length != 0) {
            argsString = "";
            for (var i = 0; i < args.length; i++) {
                argsString = argsString.concat(args[i]).concat(" ");
            }
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            synchronized (builder) {
                logger.info("{} -> Application launch parameters: {}. Shutdown in progress...",
                        Thread.currentThread().getName(), argsString);
                var msg = "";
                for (var i = 0.347d; i < 5d; i += 1.001d) {
                    msg = String.format("\u001B[1m%-10s\u001B[0m: ... %7.3f %% ...", Thread.currentThread().getName(),
                            (i * 23 >= 100) ? 100d : i * 23d);
                    logger.info(msg);
                }
                logger.info(
                        "{} -> Aplication shutdown completed. You may close any remaining console windows opened by this application!",
                        Thread.currentThread().getName());
            }
        }));
    }
}
