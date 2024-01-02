package com.module.library.EXCEPTIONS;

import lombok.Getter;

@Getter
public class InvalidStateException extends RuntimeException{
    public InvalidStateException(String message) {
        super(message);
    }
}
