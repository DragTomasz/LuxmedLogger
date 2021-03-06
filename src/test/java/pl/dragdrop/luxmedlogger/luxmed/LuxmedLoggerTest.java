package pl.dragdrop.luxmedlogger.luxmed;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import java.util.Base64;

class LuxmedLoggerTest {

    @Test
    void decodeString() {
        String password = "test";
        String login = "test";

        String encodedPassword = "dGVzdA==";
        String encodedLogin = "dGVzdA==";

        Assert.assertEquals(password, new String(Base64.getDecoder().decode(encodedPassword)));
        Assert.assertEquals(login, new String(Base64.getDecoder().decode(encodedLogin)));
    }
}