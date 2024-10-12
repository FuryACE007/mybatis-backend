package com.fidelity.exceptions;

public class InsufficientInstrumentsException extends RuntimeException {
    public InsufficientInstrumentsException(String message) {
        super(message);
    }
}