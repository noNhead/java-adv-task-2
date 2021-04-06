package org.example.service;

import org.example.User;
import org.example.dao.DataAccessObject;
import org.example.service.operation.OperationTransfer;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import static org.example.utils.Const.*;

public class Lifecycle {
    private static final Logger LOGGER = LoggerFactory.getLogger(Lifecycle.class);

    /**
     * 20 потоков операции снять со счёта положить на счёт
     * все операции должны быть потокобезопасными
     * Выполнить 1000 операций.
     * Сделать собственные исключения (не обрабатывать исключения времени выполнения)
     * Реализовать Logger
     * В конце программы распечатать итоговые балансы по счетам.
     */

    public void process() throws NoSuchAlgorithmException {
        DataAccessObject dataAccessObject = new DataAccessObject();
        ExecutorService service = Executors.newFixedThreadPool(MAX_THREAD_IN_POOL);
        BalanceOperationCallService balanceOperationCallService = new BalanceOperationCallService();
        OperationTransfer operationTransfer = new OperationTransfer(balanceOperationCallService);

        //генерация новых карт пользователя для первого запуска
        File folder = new File(dataAccessObject.getPath());
        if (Objects.requireNonNull(folder.list()).length <= MAX_USER_CARDS){
            for (int i = Objects.requireNonNull(folder.list()).length; i <= MAX_USER_CARDS; i++) {
                balanceOperationCallService.newUserCard(new User((long) i, "username", START_MONEY_TO_USER));
            }
            // повторное создание, чтобы задать в память юзеров, которых не было там
            balanceOperationCallService = new BalanceOperationCallService();
        }

        //Launching the application - calling the search for cards
        // from the folder specified in the property
        for (int i = 0; i < MAX_OPERATION_IN_PROCESS; i++) {
            service.submit(operationTransfer);
        }
        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.warn(String.valueOf(e));
            Thread.currentThread().interrupt();
        }
        LOGGER.info("successful operation: {}", balanceOperationCallService.getSuccessCounter());
        dataAccessObject.setAllUserCards(balanceOperationCallService.getUsersId());
    }
}
