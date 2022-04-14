package com.yetkin.sepet.exception.handler;

import com.yetkin.sepet.exception.GenericException;
import com.yetkin.sepet.exception.errors.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class RestExceptionHandler {


    private final MessageSource messageSource;

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<String> customHandleGenericException(GenericException ex) {
        //LocaleContextHolder.setLocale(new Locale("eng"));
        String userMessage = messageSource.getMessage(ex.getErrorCode().name(), Arrays.asList(ex.getVar1()).toArray(), LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString().replace("{", "").replace("}", ""));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = null;
        if (ex.getCause() instanceof ConstraintViolationException) {
            if (((ConstraintViolationException) ex.getCause()).getSQLException().getSQLState().equals("23503"))
                errorMessage = messageSource.getMessage(ErrorCode.FOREIGN_KEY_EXCEPTION.name(), null, LocaleContextHolder.getLocale());
            else if (((ConstraintViolationException) ex.getCause()).getSQLException().getSQLState().equals("23505"))
                errorMessage = messageSource.getMessage(((ConstraintViolationException) ex.getCause()).getConstraintName(), null, LocaleContextHolder.getLocale());
            else errorMessage = ex.getMessage();

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler({javax.validation.ConstraintViolationException.class})
    public ResponseEntity<String> handleConstraintViolation(
            javax.validation.ConstraintViolationException ex, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath() + "";
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }
        String errorMessage = errors.toString().replace("{", "").replace("}", "");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
