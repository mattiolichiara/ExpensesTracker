package com.chiara.expensestracker.Exceptions;

import lombok.Getter;

//@ResponseStatus(code = HttpStatus.NO_CONTENT)
@Getter
public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }


}
