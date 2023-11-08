package ru.hmp.hangman;

import ru.hmp.hangman.actions.ExitConsoleAction;
import ru.hmp.hangman.actions.GetHelpAction;
import ru.hmp.hangman.actions.StartGameAction;
import ru.hmp.hangman.actions.UserAction;
import ru.hmp.hangman.exceptions.LoadDictionaryException;
import ru.hmp.hangman.exceptions.LoadGraphicException;
import ru.hmp.hangman.gameprocessor.GameProcessor;
import ru.hmp.hangman.gameprocessor.GameProcessorBase;
import ru.hmp.hangman.io.*;
import ru.hmp.hangman.loader.ResourceLoader;
import ru.hmp.hangman.loader.ResourceLoaderInternal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {

        Output output = new ConsoleOutput();
        Input input = new ValidateInput(output, new ConsoleInput());
        ResourceLoader resourceLoader = new ResourceLoaderInternal();

        int numOfStages = 6;

        List<String> dictionary;
        List<String> graphics;
        String instruction;

        try {
            dictionary = resourceLoader.loadDictionary(numOfStages);
            graphics = resourceLoader.loadGraphics(numOfStages);
            instruction = resourceLoader.loadInstruction();

        } catch (LoadDictionaryException | LoadGraphicException e) {
            output.println("Error occurred while loading resources");
            output.println(String.format("Error description: %s", e.getMessage()));
            if (e.getCause() != null && e.getCause().getMessage() != null) {
                output.println(String.format("Error cause: %s", e.getCause().getMessage()));
            }
            output.println("Game terminated");
            return;
        }

        GameProcessor gameProcessor = new GameProcessorBase(input, output, dictionary, graphics, numOfStages);

        ArrayList<UserAction> actions = new ArrayList<>();
        actions.add(new StartGameAction(output, gameProcessor));
        actions.add(new GetHelpAction(output, instruction));
        actions.add(new ExitConsoleAction(output));

        new Main().fire(input, output, actions);
    }

    public void fire(Input input, Output output, List<UserAction> actions) throws Exception {

        boolean run = true;
        List<String> actionsStrings = actions.stream().
                map(UserAction::name).
                collect(Collectors.toList());

        while (run) {
            UserAction action = actions.get(InputHandler.handleSelectItems(
                    input,
                    output,
                    System.lineSeparator() + "<<< Menu >>>",
                    actionsStrings,
                    "Select action: ") - 1);
            run = action.execute(input);
        }
    }
}