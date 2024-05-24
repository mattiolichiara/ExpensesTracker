package com.chiara.expensestracker.Exceptions;

import lombok.Getter;

//@ResponseStatus(code = HttpStatus.BAD_REQUEST)
@Getter
public class BadRequestException extends Exception {

    public BadRequestException(String message) {
        super(message);
    }


}