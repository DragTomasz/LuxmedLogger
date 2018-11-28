package pl.dragdrop.luxmedlogger.luxmed;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class BadCredentialException extends RuntimeException {

    BadCredentialException() {
        log.error("Credentials are null or empty");
    }

}
