package edu.architect_711.words.service;

public class OffsetCalculator {
    public static Long regular(final Long size, final Long page) {
        return size * page;
    }
}
