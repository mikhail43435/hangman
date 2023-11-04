package ru.hmp.hangman.exceptions;

public class LoadDictionaryException extends Exception {

    public LoadDictionaryException(String message) {
        super(message);
    }

    public LoadDictionaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
