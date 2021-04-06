package org.example;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class User implements Serializable {
    private static final ReentrantLock locker = new ReentrantLock();
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private Long balance;

    public User(Long id, String username, Long balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void increaseBalance(Long increaseNumber) {
        this.balance += increaseNumber;
    }

    public void decreaseBalance(Long decreaseNumber) {
        this.balance -= decreaseNumber;
    }

    public void lock(){
        locker.lock();
    }

    public void unlock(){
        locker.unlock();
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                '}';
    }
}
