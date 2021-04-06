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
    private AtomicInteger runCounter = new AtomicInteger(0);

    public BalanceOperationCallService() {
        this.usersId = dataAccessObject.getAllUserCardName();
    }

    /**
     * Переводит средства с одного счёта на другой
     */
    public byte transferValue(Long idDecrease, Long idIncrease, Long transferAmount) {
        System.out.println(runCounter.incrementAndGet());
        System.out.println(successCounter.get());
        if (usersId.get(idDecrease) == null || usersId.get(idIncrease) == null) {
            LOGGER.warn("Transfer operation: user not found");
            Thread.currentThread().interrupt();
            return 1;
        }
        //LOGGER.info(idDecrease + " " + idIncrease + " " + transferAmount);


        if (lockerCheck(idDecrease, idIncrease)) {
            LOGGER.info("In lock: {}", Thread.currentThread().getName());
            usersId.get(idIncrease).increaseBalance(transferAmount);
            usersId.get(idDecrease).decreaseBalance(transferAmount);
            usersId.get(idIncrease).unlock();
            usersId.get(idDecrease).unlock();
            this.successCounter.incrementAndGet();
            return 0;
        } else {
            return 2;
        }
    }

    public void newUserCard(User user) {
        dataAccessObject.setUserCard(user);
    }

    public long getSuccessCounter() {
        return this.successCounter.get();
    }

    public Map<Long, User> getUsersId() {
        return this.usersId;
    }

    public AtomicInteger getRunCounter() {
        return runCounter;
    }

    private boolean lockerCheck(Long idDecrease, Long idIncrease) {
        boolean flagToUserDec = false;
        boolean flagToUserInc = false;
        boolean statement = false;
        try {
            flagToUserDec = usersId.get(idDecrease).getTryLock();
            flagToUserInc = usersId.get(idIncrease).getTryLock();
            if (flagToUserDec && flagToUserInc) {
                statement = true;
                return true;
            }
        } catch (InterruptedException e) {
            LOGGER.warn(String.valueOf(e));
            Thread.currentThread().interrupt();
        } finally {
            if (!statement) {
                if (flagToUserDec) {
                    usersId.get(idDecrease).unlock();
                }
                if (flagToUserInc) {
                    usersId.get(idIncrease).unlock();
                }
            }
        }
        return false;
    }
}
