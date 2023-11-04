package ru.hmp.hangman.gameprocessor;

import ru.hmp.hangman.io.Input;
import ru.hmp.hangman.io.InputHandler;
import ru.hmp.hangman.io.Output;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class GameProcessorBase implements GameProcessor {

    private static final char MASK_CHAR = '*';

    private final Input input;
    private final Output output;
    private final List<String> dictionary;
    private final List<String> graphics;
    private final Random random;
    private final int stagesNum;

    public GameProcessorBase(Input input,
                             Output output,
                             List<String> dictionary,
                             List<String> graphics,
                             int stagesNum) {
        this.input = input;
        this.output = output;
        this.dictionary = dictionary;
        this.graphics = graphics;
        this.random = new Random();
        this.stagesNum = stagesNum;
    }

    @Override
    public void fireGame() {

        Set<Character> revealedLetters = new HashSet<>();

        String secretWord = dictionary.get(random.nextInt(dictionary.size()));

        int[] letterPosToRevealAtStart = getNPointsInRange(secretWord.length(), 2);
        revealedLetters.add(secretWord.charAt(letterPosToRevealAtStart[0]));
        revealedLetters.add((secretWord.charAt(letterPosToRevealAtStart[1])));

        int stageIndex = 0;

        output.println(System.lineSeparator() + "Game started...");

        while (stageIndex < stagesNum) {

            String wordToShow = createWordToShow(secretWord, revealedLetters);
            if (checkWordIsFullyRevealed(wordToShow)) {
                break;
            }

            output.println(graphics.get(stageIndex));
            output.println(System.lineSeparator()
                    + String.format("Only %d attempts left!", stagesNum - stageIndex));
            output.println(String.format("Word: %s", wordToShow));

            char enteredChar = Character.toUpperCase(
                    InputHandler.handleSingleCharInput(input, output, "Input letter:"));

            if (!revealedLetters.add(enteredChar)) {
                output.println(String.format("Letter '%s' has been already entered!", enteredChar));
            }

            if (secretWord.indexOf(enteredChar) < 0) {
                stageIndex++;
                output.println("Secret word does NOT contain entered letter!");
            } else {
                output.println("Secret word does contains entered letter!");
            }
        }

        if (stageIndex == stagesNum) {
            output.println(graphics.get(stageIndex));
            output.println(System.lineSeparator() + "You fail!");
        } else {
            output.println(System.lineSeparator() + "CONGRATULATION! You have won!");
        }

        output.println(String.format("Secret word was: %s", secretWord));
        output.println("Game over");
    }

    private boolean checkWordIsFullyRevealed(String word) {
        return word.indexOf(MASK_CHAR) < 0;
    }

    private String createWordToShow(String word, Set<Character> revealedLetters) {
        StringBuilder sb = new StringBuilder();
        for (char ch : word.toCharArray()) {
            if (revealedLetters.contains(ch)) {
                sb.append(ch);
            } else {
                sb.append(MASK_CHAR);
            }
        }
        return sb.toString();
    }

    private int[] getNPointsInRange(int range, int numOfPoints) {
        if (numOfPoints > range || range <= 0 || numOfPoints <= 0) {
            return new int[0];
        }
        Set<Integer> set = new HashSet<>();
        while (set.size() < numOfPoints) {
            set.add(random.nextInt(range));
        }
        return set.stream().mapToInt(n -> n).toArray();
    }
}