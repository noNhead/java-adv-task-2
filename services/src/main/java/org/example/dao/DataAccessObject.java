package org.example.dao;

import org.example.User;
import org.example.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Properties;


public class DataAccessObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessObject.class);
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
            LOGGER.warn("A search for files in a folder was called, no files were found");
            return Collections.emptyList();
        }

        List<Long> ids = new ArrayList<>();
        for (String value: fileNames) {
            String[] strings = value.split("\\.");
            ids.add(Long.valueOf(strings[0]));
        }
        return ids;
    }

    /**
     * Получение адреса директории, где хранятся user-card из property
     * @return строку с адресом директории
     */
    public String getPath(){
        String rootPath = Objects.requireNonNull(Thread
                .currentThread()
                .getContextClassLoader()
                .getResource(""))
                .getPath();

        String propertyPath = rootPath + "config.properties";
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(propertyPath)){
            properties.load(fileInputStream);
            return properties.getProperty("userListFolder");
        } catch (IOException e) {
            LOGGER.warn(String.valueOf(e));
            return null;
        }
    }
}
