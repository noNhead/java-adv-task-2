package org.example.service.operation;

import org.example.exception.UserNotFoundException;
import org.example.service.BalanceOperationCallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static org.example.utils.Const.*;

public class OperationIncreaseMoney implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationIncreaseMoney.class);
    private final BalanceOperationCallService balanceOperationCallService;
    private final Random random = new Random();

    public OperationIncreaseMoney(BalanceOperationCallService balanceOperationCallService) {
        this.balanceOperationCallService = balanceOperationCallService;
    }

    @Override
    public void run() {
        Long id = (long) this.random.nextInt(MAX_USER_CARDS + 1);
        Long amount = (long) this.random.nextInt(MAX_TRANSFER - MIN_TRANSFER + 1) + MIN_TRANSFER;
        try {
            this.balanceOperationCallService.increaseValueInUser(id, amount);
        } catch (UserNotFoundException e) {
            LOGGER.warn(String.valueOf(e));
        }
    }
}
