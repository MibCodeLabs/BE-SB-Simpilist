package com.mib.simpilist.exception;

import com.mib.simpilist.Enum.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.GeneralSecurityException;

import static com.mib.simpilist.utililty.Utilities.generateErrorResponse;

@RestControllerAdvice
@Slf4j(topic = "GlobalExceptionHandler")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // We can add custom logging logic to handle specific errors
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbiddenException(ForbiddenException e) {
        log.error(e.getMessage());
        return generateErrorResponse(e,ErrorCodes.FORBIDDEN,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<?> handleClientException(ClientException e) {
        log.error(e.getMessage());
        return generateErrorResponse(e,ErrorCodes.BAD_REQUEST,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage());
        return generateErrorResponse(e,ErrorCodes.BAD_REQUEST,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GeneralSecurityException.class)
    public ResponseEntity<?> handleGeneralSecurityException(GeneralSecurityException e) {
        log.error(e.getMessage());
        return generateErrorResponse(e,ErrorCodes.BAD_REQUEST,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleDefaultException(Exception e) {
        if (e instanceof NullPointerException) {
            log.error("Experiencing NPE");
        } else {
            log.error("Not experiencing NPE");
        }
        log.error("Unhandled exception occurred", e);
        return generateErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ErrorCodes.INTERNAL_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NotNull HttpMessageNotReadableException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request) {

        return generateErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), ErrorCodes.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request) {
        String errors = String.join(", ", ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList());

        return generateErrorResponse(errors, ErrorCodes.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }


}
