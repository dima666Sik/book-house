package ua.house.book.auth.controller.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errorMessage = new StringBuilder();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
    }
}
