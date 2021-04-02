package org.example.service.operation;

import org.example.service.BalanceOperationCallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

import static org.example.utils.Const.*;

public class OperationIncreaseMoney implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationIncreaseMoney.class);
    private final BalanceOperationCallService balanceOperationCallService;

    public OperationIncreaseMoney(BalanceOperationCallService balanceOperationCallService){
        this.balanceOperationCallService = balanceOperationCallService;
    }

    @Override
    public void run() {
        Random random = null;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn(String.valueOf(e));
        }
        Long id = (long) Objects.requireNonNull(random).nextInt(MAX_USER_CARDS + 1);
        Long amount = (long) random.nextInt(MAX_TRANSFER - MIN_TRANSFER + 1) + MIN_TRANSFER;
        this.balanceOperationCallService.increaseValueInUser(id, amount);
    }
}
