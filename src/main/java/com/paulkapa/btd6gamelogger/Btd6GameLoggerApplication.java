package com.paulkapa.btd6gamelogger;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
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

    private static void addShutdownHook(String[] args) {
        var argsString = new StringBuilder();

        if (args != null && args.length != 0)
            for (var i = 0; i < args.length; i++)
                argsString.append(args[i] + " ");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            synchronized (starterInstance) {
                springLogger.info("{} -> Parameters: [ {} ]. Shutdown in progress...", Thread.currentThread().getName(),
                        argsString);

                var msg = "";
                for (var i = 0.347d; i < 5d; i += 1.001d) {
                    msg = String.format("\u001B[1m%-10s\u001B[0m -> ... %7.3f %% ...", Thread.currentThread().getName(),
                            (i * 23 >= 100) ? 100d : i * 23d);
                    springLogger.info(msg);
                }

                springLogger.info("{} -> Complete!", Thread.currentThread().getName());
            }
        }));

        springLogger.info("Shutdown Hook attached!");
    }

    public static void main(String[] args) throws IOException {
        starterInstance = new Btd6GameLoggerApplication();
        starterInstance.appBuilder.run(args);

        var os = System.getProperty("os.name");
        var msg = String.format("Application running on %s", os);
        logger.log(Level.INFO, msg);
        var url = "http://localhost:8080";
        msg = String.format("Trying to open application at \'%s\' ...", url);
        logger.log(Level.INFO, msg);
        os = os.toLowerCase();

        if (os.contains("win")) {
            Runtime.getRuntime().exec("cmd.exe /c start \"\" \"" + url + "\"");
        } else if (os.contains("mac")) {
            Runtime.getRuntime().exec("open \"" + url + "\"");
        } else if (os.contains("nix") || os.contains("nux")) {
            var browsers = new String[] { "epiphany", "firefox", "mozilla", "konqueror", "netscape", "opera", "links", "lynx" };
            var cmd = new StringBuilder();
            for (var i = 0; i < browsers.length; i++)
                if (i == 0)
                    cmd.append(String.format("%s \"%s\"", browsers[i], url));
                else
                    cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
            Runtime.getRuntime().exec("sh -c " + cmd.toString());
        } else {
            if (Desktop.isDesktopSupported()) {
                var desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI(url));
                    logger.log(Level.INFO, "Login page displayed!");
                } catch (IOException | URISyntaxException e) {
                    logger.log(Level.WARNING,
                            "Request to open application page in browser denied! You need to manually enter the address: \'http:\\\\localhost:8080\\\' in your default browser.",
                            e);
                    e.printStackTrace();
                }
            } else {
                var runtime = Runtime.getRuntime();
                try {
                    runtime.exec("xdg-open \"" + url + "\"");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
