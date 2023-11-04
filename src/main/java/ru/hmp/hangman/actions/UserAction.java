package ru.hmp.hangman.actions;

import ru.hmp.hangman.io.Input;

public interface UserAction {
    String name();

    boolean execute(Input input) throws Exception;
}