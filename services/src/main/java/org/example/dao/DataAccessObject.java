package org.example.dao;

import org.example.User;
import org.example.utils.Utils;

import java.io.*;
import java.util.*;


public class DataAccessObject {
    private final Utils utils = new Utils();

    /**
     * получает карту через вызов метода десериализцаии в util.Utils
     * @param id пользователя
     * @return возвращает объект класса User.class
     */
    public User getUserCard(Long id){
        return utils.deserialize(getPath() + "/" + id.toString() + ".usr");
    }

    /**
     * записывает карту через вызов метода сериализцаии в util.Utils
     * @param user карточка пользователя
     */
    public void setUserCard(User user){
        utils.serialize(getPath() + "/" +user.getId().toString() + ".usr", user);
    }

    /**
     * Получает все id и записывает их в List<Long id>
     * @return который потом и возвращает
     */
    public List<Long> getAllUserCardName(){
        File file = new File(getPath());
        String[] fileNames = file.list();
        if (fileNames == null) {
            return null;
        }

        List<Long> ids = new ArrayList<>();
        for (String value: fileNames) {
            String[] strings = value.split("\\.");
            ids.add(Long.valueOf(strings[0]));
        }
        return ids;
    }

    public String getPath(){
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String propertyPath = rootPath + "config.properties";
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(propertyPath)){
            properties.load(fileInputStream);
            return properties.getProperty("userListFolder");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
