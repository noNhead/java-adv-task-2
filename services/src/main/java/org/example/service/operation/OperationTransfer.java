package org.example.service.operation;

import org.example.service.BalanceOperationCallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import static org.example.utils.Const.*;

public class OperationTransfer implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationTransfer.class);
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
        int i = 0;
        while(true) {
            byte exitCodeValueTransfer = this.balanceOperationCallService
                    .transferValue(idOutput, idInput, amount);
            if (exitCodeValueTransfer == 0) {
                LOGGER.warn("Всё пошло так");
                break;
            } else if (exitCodeValueTransfer == 1) {
                LOGGER.warn("ЧТО-то пошло не так");
                break;
            } else if (exitCodeValueTransfer == 2) {
                LOGGER.warn("залочено");
            } else {
                LOGGER.warn("wtf");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    LOGGER.warn(String.valueOf(e));
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
