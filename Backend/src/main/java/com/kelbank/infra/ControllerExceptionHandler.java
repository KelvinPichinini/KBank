package com.kelbank.infra;

import com.kelbank.dtos.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.MissingResourceException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDuplicate(DataIntegrityViolationException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO("Dados já cadastrados para outro usuário");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleNotFound(EntityNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleOtherErrors(Exception exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage());
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
    @ExceptionHandler(MissingResourceException.class)
    public ResponseEntity handleMissingResource(MissingResourceException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage());
        return ResponseEntity.badRequest().body(exceptionDTO);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleNotReadable(HttpMessageNotReadableException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO("Requisição incorreta");
        return ResponseEntity.badRequest().body(exceptionDTO.message());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity handleAuth(AuthenticationException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO("Usuario ou senha incorretos");
        return ResponseEntity.badRequest().body(exceptionDTO.message());
    }

}
