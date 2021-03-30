package org.example.service;

import org.example.User;
import org.example.dao.DataAccessObject;

import java.util.List;


public class BalanceOperationCallService {
    private final DataAccessObject dataAccessObject = new DataAccessObject();
    private final List<Long> usersId;

    public BalanceOperationCallService() {
        this.usersId = dataAccessObject.getAllUserCardName();
    }

    /**
     * Добавляет средства на счёт
     */
    public synchronized void increaseValueInUser(Long id, Long added){
        for (Long v: usersId) {
            if (v.equals(id)) {
                User user = dataAccessObject.getUserCard(id);
                user.increaseBalance(added);
                System.out.println(user.getId() + " | " + user.getUsername() + " | " + user.getBalance());
                dataAccessObject.setUserCard(user);
                return;
            }
        }
        System.out.println("Такого пользователя не существует");
    }

    /**
     * Убавляет средства со счёта
     */
    public synchronized void decreaseValueInUser(Long id, Long subtrahend){
        for (Long v: usersId) {
            if (v.equals(id)) {
                User user = dataAccessObject.getUserCard(id);
                user.decreaseBalance(subtrahend);
                System.out.println(user.toString());
                dataAccessObject.setUserCard(user);
                return;
            }
        }
        System.out.println("Такого пользователя не существует");
    }

    /**
     * Переводит средства с одного счёта на другой
     */
    public synchronized void transferValue(Long idDecrease, Long idIncrease, Long transferAmount){
        decreaseValueInUser(idDecrease, transferAmount);
        increaseValueInUser(idIncrease, transferAmount);
    }

    public synchronized void newUserCard(User user){
        dataAccessObject.setUserCard(user);
    }
}
