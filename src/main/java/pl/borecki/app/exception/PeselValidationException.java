package pl.borecki.app.exception;

import java.io.Serial;

public class PeselValidationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public PeselValidationException(String message) {
        super(message);
    }
}