package stc.khabib.lec12_dao.dao;


import stc.khabib.lec12_dao.entity.Person;
import stc.khabib.lec12_dao.entity.Subject;

import java.sql.SQLException;
import java.util.Collection;

public interface CourseDAO {

    Collection<Person> getPersonsBySubject(Subject subject) throws SQLException;

    Collection<Subject> getSubjectsByPerson(Person person) throws SQLException;

    void linkToCourse(Person person, Subject... subject) throws SQLException;

    void linkToCourse(Subject subject, Person... person) throws SQLException;
}
