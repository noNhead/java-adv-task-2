package org.example.utils;

public final class Const {
    public static final int MAX_USER_CARDS = 20;
    public static final int MAX_TRANSFER = 100;
    public static final int MIN_TRANSFER = 10;
    public static final long START_MONEY_TO_USER = 200;
    public static final int MAX_OPERATION_IN_PROCESS = 1000;
    public static final int THE_NUMBER_OF_AVAILABLE_OPERATIONS_TO_GENERATE = 3;

    private Const() {
        throw new IllegalStateException("Utility class");
    }
}
