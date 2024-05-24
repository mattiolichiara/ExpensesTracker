package com.chiara.expensestracker.Exceptions;

import lombok.Getter;

//@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
public class HTTPRunTimeException extends RuntimeException {

    public HTTPRunTimeException(String message) {
        super(message);
    }
}
