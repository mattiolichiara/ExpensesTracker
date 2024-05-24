package com.chiara.expensestracker.Exceptions;

import lombok.Getter;

//@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
public class InternalServerErrorException extends Exception {

    public InternalServerErrorException(String message) {
        super(message);
    }


}
