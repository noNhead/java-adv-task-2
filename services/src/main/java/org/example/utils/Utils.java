package org.example.utils;

import org.example.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);
    /**
     * Сериализация в бинарный код
     * @param path путь к файлу
     * @param user объект из пользователя
     */
    public void serialize(String path, User user) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
             FileChannel channel = fileOutputStream.getChannel();
             FileLock lock = channel.lock();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(user);
        } catch (IOException e) {
            LOGGER.warn(String.valueOf(e));
        }
    }

    /**
     * Десериализация из бинарного кода
     * @param path путь к файлу
     * @return возвращает строку с текстом
     */
    public User deserialize(String path) {
        try (FileInputStream fileInputStream = new FileInputStream(path);
             FileChannel channel = fileInputStream.getChannel();
             FileLock lock = channel.lock(0, Long.MAX_VALUE, true);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (User) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.warn(String.valueOf(e));
            return null;
        }
    }

}
