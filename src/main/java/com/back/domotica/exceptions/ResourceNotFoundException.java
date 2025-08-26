package com.back.domotica.exceptions;

public class ResourceNotFoundException extends DefaultApiException {

    private static final long serialVersionUID = 202508261001L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
