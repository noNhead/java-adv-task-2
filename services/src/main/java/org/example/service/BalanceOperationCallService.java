package org.example.service;

import org.example.User;
import org.example.dao.DataAccessObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class BalanceOperationCallService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceOperationCallService.class);
    private final DataAccessObject dataAccessObject = new DataAccessObject();
    private final Map<Long, User> usersId;
    private AtomicInteger successCounter = new AtomicInteger(0);

    public BalanceOperationCallService() {
        this.usersId = dataAccessObject.getAllUserCardName();
    }

    /**
     * Переводит средства с одного счёта на другой
     */
    public short transferValue(Long idDecrease, Long idIncrease, Long transferAmount) {
        if (usersId.get(idDecrease) == null || usersId.get(idIncrease) == null) {
            LOGGER.warn("Transfer operation: user not found");
            Thread.currentThread().interrupt();
            return 1;
        }
        LOGGER.info("Transfer operation idDecrease: {}", idDecrease);
        LOGGER.info("Transfer operation idIncrease: {}", idIncrease);
        LOGGER.info("Transfer amount is: {}", transferAmount);

        User userIncrease = usersId.get(idIncrease);
        User userDecrease = usersId.get(idDecrease);

        try {
            boolean flagToUserDec = userDecrease.getTryLock();
            boolean flagToUserInc = userIncrease.getTryLock();
            if (flagToUserDec || flagToUserInc) {
                LOGGER.info("In lock: {}", Thread.currentThread().getName());
                usersId.get(idIncrease).increaseBalance(transferAmount);
                usersId.get(idDecrease).decreaseBalance(transferAmount);
                this.successCounter.incrementAndGet();
                return 0;
            } else {
                return 2;
            }
        } catch (InterruptedException e) {
            LOGGER.warn(String.valueOf(e));
            Thread.currentThread().interrupt();
        } finally {
            usersId.get(idIncrease).unlock();
            usersId.get(idDecrease).unlock();
            LOGGER.info("Out Lock: {}", Thread.currentThread().getName());
        }
        return 3;
    }

    public void newUserCard(User user){
        dataAccessObject.setUserCard(user);
    }

    public long getSuccessCounter(){
        return this.successCounter.get();
    }

    public Map<Long, User> getUsersId() {
        return this.usersId;
    }

}
