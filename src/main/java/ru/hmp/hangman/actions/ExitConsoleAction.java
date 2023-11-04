package ru.hmp.hangman.actions;


import ru.hmp.hangman.io.Input;
import ru.hmp.hangman.io.Output;

public final class ExitConsoleAction implements UserAction {
    private final Output out;

    public ExitConsoleAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Close game";
    }

    @Override
    public boolean execute(Input input) throws Exception {
        out.println("Game is closed");
        return false;
    }
}

