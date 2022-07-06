package pl.borecki.app.exception;

import java.io.Serial;

public class ExchangeProcessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public ExchangeProcessException(String message) {
        super(message);
    }
}