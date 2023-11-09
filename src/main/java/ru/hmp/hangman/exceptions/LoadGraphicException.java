package ru.hmp.hangman.exceptions;

public class LoadGraphicException extends ApplicationException {

    public LoadGraphicException(String message) {
        super(message);
    }

    public LoadGraphicException(String message, Throwable cause) {
        super(message, cause);
    }
}
