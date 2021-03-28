package org.example.service;

import org.example.UserGroup;


public class BalanceOperationCallService {
    private final UserGroup userGroup;

    public BalanceOperationCallService(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * Добавляет средства на счёт
     */
    public synchronized void increaseValueInUser(Long id, Long added){
        userGroup.increaseMoneyValue(id, added);
    }

    /**
     * Убавляет средства со счёта
     */
    public synchronized void decreaseValueInUser(Long id, Long subtrahend){
        userGroup.decreaseMoneyValue(id, subtrahend);
    }

    /**
     * Переводит средства с одного счёта на другой
     */
    public synchronized void transferValue(Long idDecrease, Long idIncrease, Long transferAmount){
        userGroup.transferMoneyValue(idDecrease, idIncrease, transferAmount);
    }
}
