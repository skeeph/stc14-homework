package stc.khabib.lec12_dao.dao;

import stc.khabib.lec12_dao.entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonDAOImpl implements PersonDAO {
    private static final String GET_ALL_PERSONS_SQL_TEMPLATE =
            "SELECT * FROM person";
    private static final String INSERT_PERSON_SQL_TEMPLATE =
            "INSERT INTO person(name, birthdate) VALUES (?,?)";
    private static final String UPDATE_PERSON_SQL_TEMPLATE =
            "UPDATE person set name=?, birthdate=? WHERE person_id=?;";
    private static final String DELETE_PERSON_SQL_TEMPLATE = "" +
            "DELETE FROM person WHERE person_id=?";

    private final Connection conn;

    public PersonDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Collection<Person> getAllPersons() throws SQLException {
        List<Person> result = new ArrayList<>();
        try (Statement st = conn.createStatement()) {
            ResultSet res = st.executeQuery(GET_ALL_PERSONS_SQL_TEMPLATE);
            while (res.next()) {
                Person p = new Person();
                p.setId(res.getInt("person_id"));
                p.setName(res.getString("name"));
                p.setBirthDate(res.getDate("birthdate").getTime());
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public void createPerson(Person person) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_PERSON_SQL_TEMPLATE)) {
            stmt.setString(1, person.getName());
            stmt.setDate(2, new Date(person.getBirthDate()));
            stmt.execute();
        }
    }

    @Override
    public void updatePerson(Person person) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_PERSON_SQL_TEMPLATE)) {
            stmt.setString(1, person.getName());
            stmt.setDate(2, new Date(person.getBirthDate()));
            stmt.setInt(3, person.getId());
            stmt.execute();
        }
    }

    @Override
    public void deletePerson(Person person) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_PERSON_SQL_TEMPLATE)) {
            stmt.setInt(1, person.getId());
            stmt.execute();
        }
    }
}
