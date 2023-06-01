package com.pragma.powerup.foodcourtmicroservice.adapters.driving.http.exceptions;

public class ClientErrorException extends RuntimeException{

    public ClientErrorException() {
    }

    public ClientErrorException(String message) {
        super(message);
    }
}
