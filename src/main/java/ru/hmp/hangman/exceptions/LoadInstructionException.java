package ru.hmp.hangman.exceptions;

public class LoadInstructionException extends Exception {

    public LoadInstructionException(String message) {
        super(message);
    }

    public LoadInstructionException(String message, Throwable cause) {
        super(message, cause);
    }
}
