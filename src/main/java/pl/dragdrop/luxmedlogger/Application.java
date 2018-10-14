package pl.dragdrop.luxmedlogger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.dragdrop.luxmedlogger.luxmed.LuxmedLogger;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class Application {

    private final LuxmedLogger luxmedLogger = new LuxmedLogger();

    public static void main(String[] args) throws IOException {
        Application application = new Application();
        log.info(String.format("Start... JVM version: %s", System.getProperty("java.version")));
        SpringApplication.run(Application.class, args);
        application.endlessSearching();
    }


    private void endlessSearching() throws IOException {
        log.info("Zaczynam....");
        luxmedLogger.findDoctor();
    }
}

