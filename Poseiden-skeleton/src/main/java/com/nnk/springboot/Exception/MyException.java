package com.nnk.springboot.Exception;

public class MyException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final String messsage;
    private final Object[] parameters;

    public MyException(String messsage, Object... parameters) {
        this.messsage = messsage;
        this.parameters = parameters;
    }

    @Override
    public String getMessage() {
        return MessagePropertieFormat.getMessage(messsage, parameters);
    }
}
