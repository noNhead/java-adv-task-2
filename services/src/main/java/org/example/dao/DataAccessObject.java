package org.example.dao;

import org.example.User;
import org.example.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DataAccessObject {
    private final Utils utils = new Utils();

    /**
     * получает карту через вызов метода десериализцаии в util.Utils
     * @param id пользователя
     * @return возвращает объект класса User.class
     */
    public User getUserCard(Long id){
        return utils.deserialize(id.toString() + ".json");
    }

    /**
     * записывает карту через вызов метода сериализцаии в util.Utils
     * @param id id пользователя
     * @param user карточка пользователя
     */
    public void setUserCard(Long id, User user){
        utils.serialize(id.toString() + ".json", user);
    }


    /**
     * Получает все id и записывает их в Map<Long id, User user>
     * @return который потом и возвращает
     */
    public Map<Long, User> getAllUserCardName(){
        File file = new File("");
        String[] fileNames = file.list();
        if (fileNames == null) {
            return null;
        }

        Map<Long, User> users = new HashMap<>();
        for (String value: fileNames) {
            String[] strings = value.split("\\.");
            User user = getUserCard(Long.valueOf(strings[0]));
            users.put(Long.valueOf(strings[0]), user);
        }
        return users;
    }
}
