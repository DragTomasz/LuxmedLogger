package pl.dragdrop.luxmedlogger.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class CredentialSupplier {

    private String path = "C:\\Users\\Tomasz\\credentials.properties";
    private File initialFile = new File(path);
    private Properties properties = new Properties();

    private String user;
    private String password;

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    void readCredential() {
        InputStream targetStream;
        try {
            targetStream = new FileInputStream(initialFile);
            properties.load(targetStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
    }
}
