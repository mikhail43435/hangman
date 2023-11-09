package ru.hmp.hangman.actions;

import ru.hmp.hangman.gameprocessor.GameProcessor;
import ru.hmp.hangman.io.Input;
import ru.hmp.hangman.io.Output;

public final class StartGameAction implements UserAction {

    private final Output output;
    private final GameProcessor gameProcessor;

    public StartGameAction(Output output, GameProcessor gameProcessor) {
        this.output = output;
        this.gameProcessor = gameProcessor;
    }

    @Override
    public String name() {
        return "Start game";
    }

    @Override
    public boolean execute(Input input) {
        output.println("Starting the game...");
        gameProcessor.startGame();
        return true;
    }
}