package org.example.service;

import org.example.User;
import org.example.dao.DataAccessObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class BalanceOperationCallService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceOperationCallService.class);
    private final DataAccessObject dataAccessObject = new DataAccessObject();
    private final Map<Long, User> usersId;
    private long successCounter = 0;

    public BalanceOperationCallService() {
        this.usersId = dataAccessObject.getAllUserCardName();
    }

    /**
     * Переводит средства с одного счёта на другой
     */
    public void transferValue(Long idDecrease, Long idIncrease, Long transferAmount) {
        if (usersId.get(idDecrease) == null || usersId.get(idIncrease) == null) {
            LOGGER.warn("Transfer operation: user not found");
            Thread.currentThread().interrupt();
            return;
        }
        LOGGER.info("Transfer operation idDecrease: {}", idDecrease);
        LOGGER.info("Transfer operation idIncrease: {}", idIncrease);
        LOGGER.info("Transfer amount is: {}", transferAmount);
        try {
            usersId.get(idIncrease).lock();
            usersId.get(idDecrease).lock();
            LOGGER.info("In lock: {}", Thread.currentThread().getName());
            usersId.get(idIncrease).increaseBalance(transferAmount);
            usersId.get(idDecrease).decreaseBalance(transferAmount);
            this.successCounter++;
        } finally {
            usersId.get(idIncrease).unlock();
            usersId.get(idDecrease).unlock();
            LOGGER.info("Out Lock: {}", Thread.currentThread().getName());
        }
    }

    public void newUserCard(User user){
        dataAccessObject.setUserCard(user);
    }

    public long getSuccessCounter(){
        return this.successCounter;
    }

    public Map<Long, User> getUsersId() {
        return this.usersId;
    }
}
