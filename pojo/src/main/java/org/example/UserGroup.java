package org.example;

import java.util.List;

public class UserGroup {
    private List<User> userBalanceList;

    public UserGroup(List<User> userBalanceList) {
        this.userBalanceList = userBalanceList;
    }

    public UserGroup() {
    }

    public List<User> getUserBalanceList() {
        return userBalanceList;
    }

    public void setUserBalanceList(List<User> userBalanceList) {
        this.userBalanceList = userBalanceList;
    }
}
