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
            throw new LoadDictionaryException("Internal error occurred while load dictionary", e);
        }

        list = list.stream().
                filter(str -> str.chars().distinct().filter(Character::isLetter).count() == wordLength).
                map(String::toUpperCase).
                collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new LoadDictionaryException("Internal error occurred while load dictionary: dictionary is empty");
        }

        return list;
    }

    @Override
    public List<String> loadGraphics(int numOfStages) throws LoadGraphicException {
        List<String> list = new ArrayList<>();
        try {
            list.add(Files.readString(Paths.get(getClass().getResource("/graphics/stage00.txt").toURI())));
            list.add(Files.readString(Paths.get(getClass().getResource("/graphics/stage01.txt").toURI())));
            list.add(Files.readString(Paths.get(getClass().getResource("/graphics/stage02.txt").toURI())));
            list.add(Files.readString(Paths.get(getClass().getResource("/graphics/stage03.txt").toURI())));
            list.add(Files.readString(Paths.get(getClass().getResource("/graphics/stage04.txt").toURI())));
            list.add(Files.readString(Paths.get(getClass().getResource("/graphics/stage05.txt").toURI())));
            list.add(Files.readString(Paths.get(getClass().getResource("/graphics/stage06.txt").toURI())));
        } catch (Exception e) {
            throw new LoadGraphicException("Internal error occurred while load graphics", e);
        }

        if (list.size() != numOfStages + 1) {
            throw new LoadGraphicException("Internal error occurred while load graphics:"
                    + " number of graphic files loaded do not match stage level");
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
