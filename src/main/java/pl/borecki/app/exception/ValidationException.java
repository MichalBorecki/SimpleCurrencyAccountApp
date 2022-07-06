package pl.borecki.app.exception;

import java.io.Serial;

public class ValidationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public ValidationException(String message) {
        super(message);
    }
}