package org.example.utils;

public final class Const {
    public static final int MAX_USER_CARDS = 19;
    public static final int MAX_TRANSFER = 100;
    public static final int MIN_TRANSFER = 10;
    public static final long START_MONEY_TO_USER = 200;
    public static final int MAX_OPERATION_IN_PROCESS = 2000;
    public static final int MAX_THREAD_IN_POOL = 2000;

    private Const() {
        throw new IllegalStateException("Utility class");
    }
}
