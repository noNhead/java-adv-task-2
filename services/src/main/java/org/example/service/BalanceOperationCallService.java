package org.example.service;

import org.example.User;
import org.example.dao.DataAccessObject;

import java.util.Map;


public class BalanceOperationCallService {
    private final DataAccessObject dataAccessObject = new DataAccessObject();
    private final Map<Long, User> userGroup;

    public BalanceOperationCallService(Map<Long, User> userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * Добавляет средства на счёт
     */
    public synchronized void increaseValueInUser(Long id, Long added){
        User user = userGroup.get(id);
        user.increaseBalance(added);
        dataAccessObject.setUserCard(user);
    }

    /**
     * Убавляет средства со счёта
     */
    public synchronized void decreaseValueInUser(Long id, Long subtrahend){
        User user = userGroup.get(id);
        user.decreaseBalance(subtrahend);
        dataAccessObject.setUserCard(user);
    }

    /**
     * Переводит средства с одного счёта на другой
     */
    public synchronized void transferValue(Long idDecrease, Long idIncrease, Long transferAmount){
        decreaseValueInUser(idDecrease, transferAmount);
        increaseValueInUser(idIncrease, transferAmount);
    }
}
