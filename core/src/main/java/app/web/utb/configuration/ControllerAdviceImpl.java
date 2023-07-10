package app.web.utb.configuration;

import app.web.utb.exception.ElementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice
class ControllerAdviceImpl {
    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class, MethodArgumentNotValidException.class})
    ResponseEntity<HashMap<String, String>> handleIllegalStateException(Exception exception) {
        String msg = exception instanceof MethodArgumentNotValidException ? "Sprawdź poprawność wprowadzonych danych"
                : exception.getLocalizedMessage();
        return createResponse(HttpStatus.BAD_REQUEST, msg);
    }

    @ExceptionHandler({BadCredentialsException.class})
    ResponseEntity<HashMap<String, String>> handleBadCredentialsException(Exception e) {
        return createResponse(HttpStatus.UNAUTHORIZED, "Niepoprawny login lub hasło");
    }


    @ExceptionHandler({DisabledException.class})
    ResponseEntity<HashMap<String, String>> handleDisabledException(Exception e) {
        return createResponse(HttpStatus.UNAUTHORIZED, "Twoje konto wymaga potwierdzenie przez token email lub administratora");
    }

    @ExceptionHandler({ElementNotFoundException.class})
    ResponseEntity<HashMap<String, String>> handleElementNotFoundException(Exception e) {
        return createResponse(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
    }


    private ResponseEntity<HashMap<String, String>> createResponse(HttpStatus httpStatus, String msg) {
        HashMap<String, String> body = new HashMap<>();
        body.put("timestamp", String.valueOf(LocalDateTime.now()));
        body.put("message", msg);
        body.put("status", String.valueOf(httpStatus.value()));
        return new ResponseEntity<>(body, httpStatus);
    }
}
