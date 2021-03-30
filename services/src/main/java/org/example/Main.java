package org.example;

import org.example.service.Lifecycle;

import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        Lifecycle lifecycle = null;
        try {
            lifecycle = new Lifecycle();
            lifecycle.process();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
