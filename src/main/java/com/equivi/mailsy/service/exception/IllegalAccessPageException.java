package com.equivi.mailsy.service.exception;


public class IllegalAccessPageException extends RuntimeException {

    private static final long serialVersionUID = -8756436786946623149L;

    private IllegalAccessPageType illegalAccessPageType;

    public IllegalAccessPageException(IllegalAccessPageType illegalAccessPageType) {
        super(illegalAccessPageType.getKeyName());
        this.illegalAccessPageType = illegalAccessPageType;
    }


    public IllegalAccessPageType getIllegalAccessPageType() {
        return illegalAccessPageType;
    }
}
