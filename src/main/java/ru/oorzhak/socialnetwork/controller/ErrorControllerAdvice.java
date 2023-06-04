package ru.oorzhak.socialnetwork.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.oorzhak.socialnetwork.exception.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        Optional<HttpStatus> httpStatus = Optional.ofNullable(HttpStatus.resolve(status.value()));
        httpStatus.orElseThrow(RuntimeException::new);

        Map<String, String> body = body(httpStatus.get(), ex);
        body.put("message", message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserWithUsernameAlreadyExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleUserWithUsernameAlreadyExists(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(UserWithEmailAlreadyExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleUserWithEmailAlreadyExists(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(UserWithUsernameNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleUserWithUsernameNotFound(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(UserWithUsernameNotFriend.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleUserWithUsernameNotFriend(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(UserNotSendFriendRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleUserNotSendFriendRequest(HttpServletRequest request, Exception ex) {
        return handleCustomException(HttpStatus.BAD_REQUEST, ex);
    }

    protected static Map<String, String> body(HttpStatus status, Exception exception) {
        Map<String, String> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now().toString());
        map.put("status", Integer.toString(status.value()));
        map.put("message", exception.getMessage());
        return map;
    }

    protected ResponseEntity<Map<String, String>> handleCustomException(HttpStatus status, Exception exception) {
        return ResponseEntity.status(status).body(body(status, exception));
    }
}
