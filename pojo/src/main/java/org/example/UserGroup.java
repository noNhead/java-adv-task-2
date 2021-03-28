package org.example;

import java.util.Map;

public class UserGroup {
    private Map<Long, User> userBalanceList;

    public UserGroup(Map userBalanceList) {
        this.userBalanceList = userBalanceList;
    }

    public Map<Long, User> getUserBalanceList() {
        return userBalanceList;
    }

    public void setUserBalanceList(Map<Long, User> userBalanceList) {
        this.userBalanceList = userBalanceList;
    }

    public void decreaseMoneyValue(Long id, Long subtrahend){
        User user = userBalanceList.get(id);
        user.decreaseBalance(subtrahend);
        userBalanceList.replace(id, user);
    }

    public void increaseMoneyValue(Long id, Long added){
        User user = userBalanceList.get(id);
        user.increaseBalance(added);
        userBalanceList.replace(id, user);
    }

    public void transferMoneyValue(Long idDecrease, Long idIncrease, Long amountValue){
        User userDecrease = userBalanceList.get(idDecrease);
        userDecrease.decreaseBalance(amountValue);
        User userIncrease = userBalanceList.get(idIncrease);
        userIncrease.increaseBalance(amountValue);

        userBalanceList.replace(idDecrease, userDecrease);
        userBalanceList.replace(idIncrease, userIncrease);
    }
}
