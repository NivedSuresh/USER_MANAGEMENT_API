package com.module.library.EXCEPTIONS;

import lombok.Getter;

@Getter
public class DatabaseConnectionFailedException extends RuntimeException{
    public DatabaseConnectionFailedException(String message) {
        super(message);
    }
}
