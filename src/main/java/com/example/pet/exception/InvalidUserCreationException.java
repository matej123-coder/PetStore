package com.example.pet.exception;

public class InvalidUserCreationException extends RuntimeException{
    public InvalidUserCreationException(String message){
        super(message);
    }
}
