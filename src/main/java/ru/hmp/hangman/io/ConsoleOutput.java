package ru.hmp.hangman.io;

public final class ConsoleOutput implements Output {

    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }
}