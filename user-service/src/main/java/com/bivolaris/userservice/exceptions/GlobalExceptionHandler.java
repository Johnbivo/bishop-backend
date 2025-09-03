package com.bivolaris.userservice.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(UsersNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsersNotFound(UsersNotFoundException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.NOT_FOUND.value());
        errorBody.put("error", "User Not Found");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/users");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Validation Failed");

        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage()
                ));

        errorBody.put("message", fieldErrors);
        errorBody.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }


    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<Map<String, Object>> handleUserCreation(UserCreationException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "User Creation Failed");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/users");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    @ExceptionHandler(UserAddressNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserAddressException(UserAddressNotFoundException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.NOT_FOUND.value());
        errorBody.put("error", "Address Not Found");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/user-addresses");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }


    @ExceptionHandler(UserAddressCreationException.class)
    public ResponseEntity<Map<String, Object>> handleUserCreation(UserAddressCreationException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now().format(formatter));
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "User Creation Failed");
        errorBody.put("message", ex.getMessage());
        errorBody.put("path", "/user-addresses");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }
}
