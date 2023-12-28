package com.module.library.EXCEPTIONS;

import lombok.Getter;

@Getter
public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
