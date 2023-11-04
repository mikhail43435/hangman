package ru.hmp.hangman.loader;

import ru.hmp.hangman.exceptions.LoadDictionaryException;
import ru.hmp.hangman.exceptions.LoadGraphicException;
import ru.hmp.hangman.exceptions.LoadInstructionException;

import java.util.List;

public interface ResourceLoader {
    List<String> loadDictionary(int wordLength) throws LoadDictionaryException;

    List<String> loadGraphics(int numOfStages) throws LoadGraphicException;

    String loadInstruction() throws LoadInstructionException;
}