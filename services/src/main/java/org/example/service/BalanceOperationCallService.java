package org.example.service;

import org.example.User;
import org.example.dao.DataAccessObject;
import org.example.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class BalanceOperationCallService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceOperationCallService.class);
    private final DataAccessObject dataAccessObject = new DataAccessObject();
    private final List<Long> usersId;

    public BalanceOperationCallService() {
        this.usersId = dataAccessObject.getAllUserCardName();
    }

    /**
     * Добавляет средства на счёт
     */
    public synchronized void increaseValueInUser(Long id, Long added){
        try {
            for (Long v: usersId) {
                if (v.equals(id)) {
                    User user = dataAccessObject.getUserCard(id);
                    user.increaseBalance(added);
                    LOGGER.info("The method of adding funds from the user's account was called, " +
                            "\ndata after the change {}", user);
                    dataAccessObject.setUserCard(user);
                    return;
                }
            }
            throw new UserNotFoundException("This user does not exist");
        } catch (UserNotFoundException e) {
            LOGGER.warn(String.valueOf(e));
        }
    }

    /**
     * Убавляет средства со счёта
     */
    public synchronized void decreaseValueInUser(Long id, Long subtrahend){
        try {
            for (Long v: usersId) {
                if (v.equals(id)) {
                    User user = dataAccessObject.getUserCard(id);
                    user.decreaseBalance(subtrahend);
                    LOGGER.info("The method of adding funds from the user's account was called, " +
                            "\ndata after the change {}", user);
                    dataAccessObject.setUserCard(user);
                    return;
                }
            }
            throw new UserNotFoundException("This user does not exist");
        } catch (UserNotFoundException e) {
            LOGGER.warn(String.valueOf(e));
        }
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
