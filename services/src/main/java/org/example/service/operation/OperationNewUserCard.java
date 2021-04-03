package org.example.service.operation;

import org.example.User;
import org.example.service.BalanceOperationCallService;

import static org.example.utils.Const.MAX_USER_CARDS;
import static org.example.utils.Const.START_MONEY_TO_USER;

public class OperationNewUserCard implements Runnable{
    private final BalanceOperationCallService balanceOperationCallService;

    public OperationNewUserCard(BalanceOperationCallService balanceOperationCallService) {
        this.balanceOperationCallService = balanceOperationCallService;
    }

    @Override
    public void run() {
        for (int i = 0; i < MAX_USER_CARDS; i++) {
            User user = new User((long) i, "username", START_MONEY_TO_USER);
            balanceOperationCallService.newUserCard(user);

        }
    }
}
