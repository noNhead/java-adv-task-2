package org.example.service.operation;

import org.example.service.BalanceOperationCallService;

import java.util.Random;

import static org.example.utils.Const.*;

public class OperationDecreaseMoney implements Runnable {
    private final BalanceOperationCallService balanceOperationCallService;
    private final Random random = new Random();

    public OperationDecreaseMoney(BalanceOperationCallService balanceOperationCallService) {
        this.balanceOperationCallService = balanceOperationCallService;
    }

    @Override
    public void run() {
        Long id = (long) this.random.nextInt(MAX_USER_CARDS + 1);
        Long amount = (long) this.random.nextInt(MAX_TRANSFER - MIN_TRANSFER + 1) + MIN_TRANSFER;
        this.balanceOperationCallService.decreaseValueInUser(id, amount);
    }
}
