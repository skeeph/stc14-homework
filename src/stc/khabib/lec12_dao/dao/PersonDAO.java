package stc.khabib.lec12_dao.dao;

import stc.khabib.lec12_dao.entity.Person;

import java.sql.SQLException;
import java.util.Collection;

public interface PersonDAO {
    Collection<Person> getAllPersons() throws SQLException;

    void createPerson(Person Person) throws SQLException;

    void updatePerson(Person Person) throws SQLException;

    void deletePerson(Person Person) throws SQLException;
}
