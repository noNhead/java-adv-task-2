package org.example;

import org.slf4j.Logger;
import org.example.service.Lifecycle;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Lifecycle lifecycle = null;
        try {
            lifecycle = new Lifecycle();
            lifecycle.process();
        } catch (NoSuchAlgorithmException | InterruptedException e) {
            LOGGER.warn(String.valueOf(e));
        }
    }
}
