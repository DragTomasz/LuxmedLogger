package pl.dragdrop.luxmedlogger.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CredentialSupplierTest {

    CredentialSupplier cs = new CredentialSupplier();

    @Test
    void readCredential() {
     cs.readCredential();
     log.info(cs.getPassword());
     log.info(cs.getUser());
    }
}