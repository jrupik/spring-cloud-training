package pl.training.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionTransferObject> onException(Exception exception, Locale locale) {
        exception.printStackTrace();
        return createResponse(exception, INTERNAL_SERVER_ERROR, locale);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ExceptionTransferObject> onUserNotFoundException(PaymentNotFoundException exception, Locale locale) {
        return createResponse(exception, NOT_FOUND, locale);
    }

    private ResponseEntity<ExceptionTransferObject> createResponse(Exception exception, HttpStatus status, Locale locale) {
        var exceptionName = exception.getClass().getSimpleName();
        String description;
        try {
            description = messageSource.getMessage(exception.getClass().getSimpleName(), null, locale);
        } catch (NoSuchMessageException otherException) {
            description = exceptionName;

        }
        exception.printStackTrace();
        return ResponseEntity.status(status).body(new ExceptionTransferObject(description, LocalDateTime.now()));
    }

}