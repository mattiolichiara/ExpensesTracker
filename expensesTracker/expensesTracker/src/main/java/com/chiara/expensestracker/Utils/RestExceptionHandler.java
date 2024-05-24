package com.chiara.expensestracker.Utils;

import com.chiara.expensestracker.DTOs.GeneralResponses.GenericResponse;
import com.chiara.expensestracker.Exceptions.BadRequestException;
import com.chiara.expensestracker.Exceptions.HTTPRunTimeException;
import com.chiara.expensestracker.Exceptions.InternalServerErrorException;
import com.chiara.expensestracker.Exceptions.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@EnableWebMvc
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity customExceptionHandler(NotFoundException e) {
        return new ResponseEntity(new GenericResponse<>(404, HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity customExceptionHandler(BadRequestException e) {
        return new ResponseEntity(new GenericResponse<>(400, HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity customExceptionHandler(InternalServerErrorException e) {
        return new ResponseEntity(new GenericResponse<>(500, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(HTTPRunTimeException.class)
    public ResponseEntity customExceptionHandler(HTTPRunTimeException e) {
        return new ResponseEntity(new GenericResponse<>(500, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
