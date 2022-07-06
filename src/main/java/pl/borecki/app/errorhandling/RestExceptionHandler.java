package pl.borecki.app.errorhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.borecki.app.exception.ExchangeProcessException;
import pl.borecki.app.exception.RecordNotFoundException;
import pl.borecki.app.exception.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<?> handleRecordNotFoundException(RecordNotFoundException e) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
        log.error(apiError.getMessage());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<?> handleValidationException(ValidationException e) {
        ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, e.getClass().getSimpleName(), e.getMessage());
        log.error(apiError.getMessage());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(ExchangeProcessException.class)
    protected ResponseEntity<?> handleExchangeProcessException(ExchangeProcessException e) {
        ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, e.getClass().getSimpleName(), e.getMessage());
        log.error(apiError.getMessage());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleJavaxConstraintViolationException(ConstraintViolationException e) {
        StringBuilder constraintViolations = new StringBuilder();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            constraintViolations.append(violation.getMessage());
            constraintViolations.append(". ");
        }
        ApiError apiError = new ApiError(
                HttpStatus.UNPROCESSABLE_ENTITY, e.getClass().getSimpleName(), constraintViolations.toString());
        log.info(apiError.getMessage());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

}