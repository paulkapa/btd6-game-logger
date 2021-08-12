package com.paulkapa.btd6gamelogger;

import java.util.logging.Logger;

import com.paulkapa.secret1.util.console.logging.CustomLoggerProvider;

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

    private static Btd6GameLoggerApplication starterInstance;
    private SpringApplicationBuilder appBuilder = null;

    public static final Logger logger = CustomLoggerProvider.getLogger(Btd6GameLoggerApplication.class.getName());
    public static final org.slf4j.Logger springLogger = LoggerFactory.getLogger(Btd6GameLoggerApplication.class);

    Btd6GameLoggerApplication() {
        if (this.appBuilder == null)
            this.appBuilder = new SpringApplicationBuilder(
                    Btd6GameLoggerApplication.class).web(WebApplicationType.SERVLET);
        addShutdownHook(null);
    }

    public static void main(String[] args) {
        starterInstance = new Btd6GameLoggerApplication();
        starterInstance.appBuilder.run(args);
    }

    private static void addShutdownHook(String[] args) {
        var argsString = new StringBuilder();

        if (args != null && args.length != 0)
            for (var i = 0; i < args.length; i++)
                argsString.append(args[i] + " ");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            synchronized (starterInstance) {
                springLogger.info("{} -> Parameters: [ {} ]. Shutdown in progress...",
                        Thread.currentThread().getName(), argsString);

                var msg = "";
                for (var i = 0.347d; i < 5d; i += 1.001d) {
                    msg = String.format("\u001B[1m%-10s\u001B[0m -> ... %7.3f %% ...", Thread.currentThread().getName(), (i * 23 >= 100) ? 100d : i * 23d);
                    springLogger.info(msg);
                }

                springLogger.info(
                        "{} -> Complete!",
                        Thread.currentThread().getName());
            }
        }));

        springLogger.info("Shutdown Hook attached!");
    }
}
