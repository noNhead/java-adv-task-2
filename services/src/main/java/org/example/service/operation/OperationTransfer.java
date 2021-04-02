package org.example.service.operation;

import org.example.service.BalanceOperationCallService;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import static org.example.utils.Const.*;

public class OperationTransfer implements Runnable {
    private final Random random;
    private final BalanceOperationCallService balanceOperationCallService;

    public OperationTransfer(BalanceOperationCallService balanceOperationCallService) throws NoSuchAlgorithmException {
        this.balanceOperationCallService = balanceOperationCallService;
        this.random = SecureRandom.getInstanceStrong();
    }

    @Override
    public void run() {
        Long idOutput = (long) this.random.nextInt(MAX_USER_CARDS + 1);
        Long idInput = (long) this.random.nextInt(MAX_USER_CARDS + 1);
        Long amount = (long) this.random.nextInt(MAX_TRANSFER - MIN_TRANSFER + 1) + MIN_TRANSFER;
        this.balanceOperationCallService.transferValue(idOutput, idInput, amount);
    }
}
