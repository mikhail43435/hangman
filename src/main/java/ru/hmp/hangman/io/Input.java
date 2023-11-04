package ru.hmp.hangman.io;

public interface Input {
    String askStr(String question);

    int askInt(String question);
}
