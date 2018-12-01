package pl.dragdrop.luxmedlogger.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@Configuration
public class CredentialSupplier {

    @Value("${credential.path}")
    private String path;

    private String user;
    private String password;

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void loadCredential() {
        File file = new File(path);
        Properties properties = new Properties();
        InputStream targetStream;
        try {
            targetStream = new FileInputStream(file);
            properties.load(targetStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
    }
}
