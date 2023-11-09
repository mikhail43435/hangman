package ru.hmp.hangman;

import ru.hmp.hangman.actions.ExitConsoleAction;
import ru.hmp.hangman.actions.GetHelpAction;
import ru.hmp.hangman.actions.StartGameAction;
import ru.hmp.hangman.actions.UserAction;
import ru.hmp.hangman.exceptions.ApplicationException;
import ru.hmp.hangman.gameprocessor.GameProcessor;
import ru.hmp.hangman.gameprocessor.GameProcessorBase;
import ru.hmp.hangman.io.*;
import ru.hmp.hangman.loader.ResourceLoader;
import ru.hmp.hangman.loader.ResourceLoaderInternal;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        try {
            Output output = new ConsoleOutput();
            Input input = new ValidateInput(output, new ConsoleInput());
            ResourceLoader resourceLoader = new ResourceLoaderInternal();

            int numOfStages = Integer.parseInt(ResourceBundle.getBundle("system").getString("numOfStages"));

            List<String> dictionary = resourceLoader.loadDictionary(numOfStages);
            List<String> graphics = resourceLoader.loadGraphics(numOfStages);
            String instruction = resourceLoader.loadInstruction();

            GameProcessor gameProcessor = new GameProcessorBase(input, output, dictionary, graphics, numOfStages);

            ArrayList<UserAction> actions = new ArrayList<>();
            actions.add(new StartGameAction(output, gameProcessor));
            actions.add(new GetHelpAction(output, instruction));
            actions.add(new ExitConsoleAction(output));

            new Main().run(input, output, actions);

        } catch (Exception e) {
            if (e instanceof ApplicationException) {
                System.out.println("Error occurred while loading game resources");
            } else {
                System.out.println("Error occurred while running application");
            }
            System.out.printf("Error description: %s%n", e.getMessage());
            if (e.getCause() != null && e.getCause().getMessage() != null) {
                System.out.printf("Error cause: %s%n", e.getCause().getMessage());
            }
            System.out.println("Game terminated");
        }
    }

    public void run(Input input, Output output, List<UserAction> actions) {
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