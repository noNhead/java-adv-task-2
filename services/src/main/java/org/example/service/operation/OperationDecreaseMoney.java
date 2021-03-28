package org.example.service.operation;


import org.example.service.BalanceOperationCallService;

public class OperationDecreaseMoney implements Runnable {
    private final BalanceOperationCallService balanceOperationCallService;
    private final Long id;
    private final Long amount;

    OperationDecreaseMoney(BalanceOperationCallService balanceOperationCallService,
                           Long id,
                           Long amount){
        this.balanceOperationCallService = balanceOperationCallService;
        this.id = id;
        this.amount = amount;
    }

    @Override
    public void run() {
        this.balanceOperationCallService.decreaseValueInUser(id, amount);
    }
}
