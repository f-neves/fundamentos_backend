package br.ufg.inf.api.exception;

public class BadRequestException extends Exception {
    
    public BadRequestException(String message) {
        super(message);
    }
}
