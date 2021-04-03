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
     * Добавляет средства на счёт
     */
    public synchronized void increaseValueInUser(Long id, Long added) {
        if (usersId.get(id) == null) {
            LOGGER.warn("operation increase: user not found");
            Thread.currentThread().interrupt();
            return;
        }
        locker.lock();
        User user = usersId.get(id);
        user.increaseBalance(added);
        usersId.replace(id, user);
        this.successCounter++;
        locker.unlock();
        //LOGGER.info("The method of adding funds from the user's account was called, " +
        //        "\ndata after the change {}", user);
    }

    /**
     * Убавляет средства со счёта
     */
    public synchronized void decreaseValueInUser(Long id, Long subtrahend) {
        if (usersId.get(id) == null) {
            LOGGER.warn("Operation decrease: user not found");
            Thread.currentThread().interrupt();
            return;
        }
        locker.lock();
        User user = usersId.get(id);
        user.decreaseBalance(subtrahend);
        usersId.replace(id, user);
        this.successCounter++;
        locker.unlock();
        //LOGGER.info("The method of subtrahend funds from the user's account was called, " +
        //            "\ndata after the change {}", user);
    }

    /**
     * Переводит средства с одного счёта на другой
     */
    public synchronized void transferValue(Long idDecrease, Long idIncrease, Long transferAmount) {
        if (usersId.get(idDecrease) == null || usersId.get(idIncrease) == null) {
            LOGGER.warn("Transfer operation: user not found");
            Thread.currentThread().interrupt();
            return;
        }
        decreaseValueInUser(idDecrease, transferAmount);
        increaseValueInUser(idIncrease, transferAmount);
        this.successCounter--;
        //Здесь используется вычитание, потому что два вызывающих процесса
        // уже увеличивают каждый значение счётчика на 1, то есть в сумме на 2
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
