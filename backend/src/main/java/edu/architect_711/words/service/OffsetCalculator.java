package edu.architect_711.words.service;

public class OffsetCalculator {
    public static int offset(final int size, final int page) {
        return size * page;
    }

    public static int page(final int offset, final int size) {
        return offset / size;
    }
}
