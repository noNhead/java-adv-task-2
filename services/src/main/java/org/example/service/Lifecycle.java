package org.example.service;

import org.example.dao.DataAccessObject;
import org.example.service.operation.OperationDecreaseMoney;
import org.example.service.operation.OperationIncreaseMoney;
import org.example.service.operation.OperationNewUserCard;
import org.example.service.operation.OperationTransfer;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import static org.example.utils.Const.*;

public class Lifecycle {
    public static final int MAX_THREAD_IN_POOL = 20;
    public static final Logger LOGGER = LoggerFactory.getLogger(Lifecycle.class);

    private final Random random;

    public Lifecycle() throws NoSuchAlgorithmException {
        this.random = SecureRandom.getInstanceStrong();
    }

    /**
     * 20 потоков операции снять со счёта положить на счёт
     * все операции должны быть потокобезопасными
     * Выполнить 1000 операций.
     * Сделать собственные исключения (не обрабатывать исключения времени выполнения)
     * Реализовать Logger
     * В конце программы распечатать итоговые балансы по счетам.
     */

    public void process() throws NoSuchAlgorithmException, InterruptedException {
        DataAccessObject dataAccessObject = new DataAccessObject();
        ExecutorService service = Executors.newFixedThreadPool(MAX_THREAD_IN_POOL);
        BalanceOperationCallService balanceOperationCallService = new BalanceOperationCallService();
        OperationNewUserCard operationNewUserCard = new OperationNewUserCard(balanceOperationCallService);
        OperationTransfer operationTransfer = new OperationTransfer(balanceOperationCallService);
        OperationDecreaseMoney operationDecreaseMoney = new OperationDecreaseMoney(balanceOperationCallService);
        OperationIncreaseMoney operationIncreaseMoney = new OperationIncreaseMoney(balanceOperationCallService);

        //генерация новых карт пользователя для первого запуска
        File folder = new File(dataAccessObject.getPath());
        if (Objects.requireNonNull(folder.list()).length <= 0){
            for (int i = 0; i < MAX_USER_CARDS; i++) {
                new Thread(operationNewUserCard).start();
            }
        }

        //Launching the application - calling the search for cards
        // from the folder specified in the property
        for (int i = 0; i < MAX_OPERATION_IN_PROCESS; i++) {
            int command = this.random.nextInt(THE_NUMBER_OF_AVAILABLE_OPERATIONS_TO_GENERATE);
            if (command == 0) {
                service.submit(operationDecreaseMoney);
            } else if (command == 1) {
                service.submit(operationIncreaseMoney);
            } else {
                service.submit(operationTransfer);
            }
        }
        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        LOGGER.info("successful operation: {}", balanceOperationCallService.getSuccessCounter());
        dataAccessObject.setAllUserCards(balanceOperationCallService.getUsersId());
    }
}
