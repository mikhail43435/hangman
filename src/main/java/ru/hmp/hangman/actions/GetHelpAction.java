package ru.hmp.hangman.actions;

import ru.hmp.hangman.io.Input;
import ru.hmp.hangman.io.Output;

public final class GetHelpAction implements UserAction {

    private final Output out;
    private final String instruction;

    public GetHelpAction(Output out, String instruction) {
        this.out = out;
        this.instruction = instruction;
    }

    @Override
    public String name() {
        return "Get rules";
    }

    @Override
    public boolean execute(Input input) {
        out.println(System.lineSeparator()
                + "Game rules:"
                + System.lineSeparator()
                + instruction);
        return true;
    }
}