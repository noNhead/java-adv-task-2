package org.example.dao;

import java.util.List;

public class DataAccessObject {
    /**
     * Карточка:
     * username, id, balance
     * хранится в бинарном файле
     *
     *
     * При запуске приложение должно сканировать папку с учётными данными
     */


    /**
     * получить карточку пользователя
     * @param id карточки пользователя (название файла)
     * @return возвращает бинарный код
     */
    public String getUserCard(Long id){
        return null;
    }

    /**
     * Перезаписывает карточку пользователя
     * @param id карточки пользователя (название файла)
     */
    public void setUserCard(Long id){

    }

    /**
     * Получает все названия карточек из директории
     * @return Возвращает List названий карточек
     */
    public List<String> getAllUserCardName(){
        return null;
    }
}
