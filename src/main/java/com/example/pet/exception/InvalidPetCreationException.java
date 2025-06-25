package com.example.pet.exception;

public class InvalidPetCreationException extends RuntimeException{
    public InvalidPetCreationException(String message){
        super(message);
    }
}
