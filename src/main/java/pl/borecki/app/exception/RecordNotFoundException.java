package pl.borecki.app.exception;

import java.io.Serial;

public class RecordNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public RecordNotFoundException(String message) {
        super(message);
    }
}