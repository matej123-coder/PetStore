package com.example.pet.exception;

public class InvalidBuyException extends RuntimeException{
    public InvalidBuyException(String message){
        super(message);
    }
}
