package com.nnk.poseidon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MyExceptionNotFoundException extends RuntimeException {
    public MyExceptionNotFoundException() {
        super();
    }
//    public MyExceptionNotFoundException(String message, Throwable cause) {
//        super(message, cause);
//    }
//    public MyExceptionNotFoundException(String message) {
//        super(message);
//    }
//    public MyExceptionNotFoundException(Throwable cause) {
//        super(cause);
//    }

    public MyExceptionNotFoundException(String message, Object... parameters) {
        super(MessagePropertieFormat.getMessage(message, parameters));
    }
}
