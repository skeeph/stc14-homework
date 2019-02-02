package khabib.lec12_dao.dao;


import khabib.lec12_dao.entity.Person;
import khabib.lec12_dao.entity.Subject;

import java.sql.SQLException;
import java.util.Collection;

public interface CourseDAO {

    /**
     * Получить список всех студентов, изучающих данный предмет
     *
     * @param subject предмет
     * @return список студентов
     * @throws SQLException ошибка работы с БД
     */
    Collection<Person> getPersonsBySubject(Subject subject) throws SQLException;

    /**
     * Получить список предметов, изучаемых данным студентом
     *
     * @param person студент
     * @return список предметов
     * @throws SQLException ошибка работы с БД
     */
    Collection<Subject> getSubjectsByPerson(Person person) throws SQLException;

    /**
     * Записать данного студента, на несколько предметов
     *
     * @param person   студент
     * @param subjects список предметов
     * @throws SQLException ошибка работы с БД
     */
    void linkToCourse(Person person, Subject... subjects) throws SQLException;

    /**
     * Записать на данный предмет нескольких студентов
     *
     * @param subject предмет
     * @param persons список студентов
     * @throws SQLException Ошибка работы с БД
     */
    void linkToCourse(Subject subject, Person... persons) throws SQLException;
}
