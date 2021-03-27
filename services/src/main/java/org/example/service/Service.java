package org.example.service;

import org.example.User;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private final List<User> users = new ArrayList<>();
    /**
     * Добавляет средства на счёт
     */
    public synchronized void plus(){
        users.get(0).increaseBalance(0L);
    }

    /**
     * Убавляет средства со счёта
     */
    public synchronized void minus(){
        users.get(0).decreaseBalance(0L);
    }

    /**
     * Переводит средства с одного счёта на другой
     */
    public synchronized void transfer(){
        users.get(0).decreaseBalance(0L);
        users.get(1).increaseBalance(0L);
    }
}
