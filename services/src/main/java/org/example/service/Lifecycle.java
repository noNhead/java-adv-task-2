package org.example.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lifecycle {
    public static final int MAX_THREAD_IN_POOL = 20;

    /**
     * 20 потоков операции снять со счёта положить на счёт
     * Отдельный модуль для высокоуровневых операций с пользователем (учётно записью)
     * все операции должны быть потокобезопасными
     * Выполнить 1000 операций.
     * Сделать собственные исключения (не обрабатывать исключения времени выполнения)
     * Реализовать Logger
     * В конце программы распечатать итоговые балансы по счетам.
     */

    public void lifecycle(){
        ExecutorService service = Executors.newFixedThreadPool(MAX_THREAD_IN_POOL);

        //some operation

        service.shutdown();
    }
}
