package org.example.service.operation;

import org.example.service.BalanceOperationCallService;

public class OperationTransfer implements Runnable {
    private final BalanceOperationCallService balanceOperationCallService;
    private final Long idOutput;
    private final Long idInput;
    private final Long amount;

    OperationTransfer(BalanceOperationCallService balanceOperationCallService,
                      Long idOutput,
                      Long idInput,
                      Long amount){
        this.balanceOperationCallService = balanceOperationCallService;
        this.idOutput = idOutput;
        this.idInput = idInput;
        this.amount = amount;
    }

    @Override
    public void run() {
        this.balanceOperationCallService.transferValue(idOutput, idInput, amount);
    }
}
