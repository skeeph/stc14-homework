package khabib.lec12_dao.dao;

import khabib.lec12_dao.entity.Person;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Интерфейс для управления студентами
 */
public interface PersonDAO {
    /**
     * Получить список всех студентов
     *
     * @return список всех студентов
     * @throws SQLException ошибка работы с БД
     */
    Collection<Person> getAllPersons() throws SQLException;

    /**
     * Сохранить предмет в БД
     *
     * @param person объект-студент
     * @throws SQLException ошибка работы с БД
     */
    void createPerson(Person person) throws SQLException;

    /**
     * Изменить предмет в БД
     *
     * @param person объект-студент
     * @throws SQLException ошибка работы с БД
     */
    void updatePerson(Person person) throws SQLException;

    /**
     * Удалить предмет из БД
     *
     * @param person объект-студент
     * @throws SQLException ошибка работы с БД
     */
    void deletePerson(Person person) throws SQLException;
}
