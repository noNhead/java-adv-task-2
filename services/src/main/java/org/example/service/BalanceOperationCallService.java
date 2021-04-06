package org.example.service;

import org.example.User;
import org.example.dao.DataAccessObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


public class BalanceOperationCallService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceOperationCallService.class);
    private final DataAccessObject dataAccessObject = new DataAccessObject();
    private final Map<Long, User> usersId;
    private long successCounter = 0;
    private final ReentrantLock locker = new ReentrantLock();

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
            locker.lock();
            LOGGER.info("In lock");
            usersId.get(idIncrease).increaseBalance(transferAmount);
            usersId.get(idDecrease).decreaseBalance(transferAmount);
        } finally {
            locker.unlock();
        }
        this.successCounter++;
        //LOGGER.info("Transfer from two operations from above");
    }

    public synchronized void newUserCard(User user){
        dataAccessObject.setUserCard(user);
    }

    public synchronized long getSuccessCounter(){
        return this.successCounter;
    }

    public synchronized Map<Long, User> getUsersId() {
        return this.usersId;
    }
}
