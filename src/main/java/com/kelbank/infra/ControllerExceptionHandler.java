package com.kelbank.infra;

import com.kelbank.dtos.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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


}
