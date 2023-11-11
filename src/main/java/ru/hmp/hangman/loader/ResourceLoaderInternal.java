package ru.hmp.hangman.loader;

import ru.hmp.hangman.exceptions.LoadDictionaryException;
import ru.hmp.hangman.exceptions.LoadGraphicException;
import ru.hmp.hangman.exceptions.LoadInstructionException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceLoaderInternal implements ResourceLoader {

    @Override
    public List<String> loadDictionary(int wordLength) throws LoadDictionaryException {

        List<String> list;

        try {
            list = Files.readAllLines(Paths.get(getClass().getResource("/words_list.txt").toURI()));
        } catch (Exception e) {
            throw new LoadDictionaryException("Internal error while load dictionary", e);
        }

        list = list.stream().
                filter(str -> str.chars().distinct().filter(Character::isLetter).count() == wordLength).
                map(String::toUpperCase).
                collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new LoadDictionaryException("Internal error while load dictionary: dictionary is empty");
        }
        return list;
    }

    @Override
    public List<String> loadGraphics(int numOfStages) throws LoadGraphicException {
        List<String> list = new ArrayList<>();
        try {

            for (int i = 0; i <= numOfStages; i++) {
                String name;
                if (i < 10) {
                    name = String.format("/graphics/stage0%d.txt", i);
                } else {
                    name = String.format("/graphics/stage%d.txt", i);
                }
                list.add(Files.readString(Paths.get(getClass().getResource(name).toURI())));
            }
        } catch (Exception e) {
            throw new LoadGraphicException("Internal error while load graphics files", e);
        }
        return list;
    }

    @Override
    public String loadInstruction() throws LoadInstructionException {
        String result;
        try {
            result = String.join("", Files.
                    readAllLines(Paths.get(getClass().
                            getResource("/how_to_play_instruction.txt").toURI())));
        } catch (Exception e) {
            throw new LoadInstructionException("Internal error occurred while load instruction", e);
        }

        return result;
    }
}