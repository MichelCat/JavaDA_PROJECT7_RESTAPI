package com.nnk.springboot.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MyExceptionBadRequestException extends RuntimeException {
    public MyExceptionBadRequestException() {
        super();
    }

    public MyExceptionBadRequestException(String message, Object... parameters) {
        super(MessagePropertieFormat.getMessage(message, parameters));
    }
}
