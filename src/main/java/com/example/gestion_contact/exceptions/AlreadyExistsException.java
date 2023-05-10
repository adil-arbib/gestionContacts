package com.example.gestion_contact.exceptions;

public class AlreadyExistsException extends Exception{

    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

}
