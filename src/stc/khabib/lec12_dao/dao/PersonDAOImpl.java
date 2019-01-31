package stc.khabib.lec12_dao.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stc.khabib.lec12_dao.entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Реализация интерфейса управления студентами
 */
@SuppressWarnings("Duplicates")
public class PersonDAOImpl implements PersonDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDAOImpl.class);
    /**
     * Запрос для получения всех студентов
     */
    private static final String GET_ALL_PERSONS_SQL_TEMPLATE =
            "SELECT * FROM person";

    /**
     * Шаблон запроса создания студента
     */
    private static final String INSERT_PERSON_SQL_TEMPLATE =
            "INSERT INTO person(name, birthdate) VALUES (?,?) RETURNING person_id;";
    /**
     * Шаблон запроса изменения студента
     */
    private static final String UPDATE_PERSON_SQL_TEMPLATE =
            "UPDATE person set name=?, birthdate=? WHERE person_id=?;";
    /**
     * Шаблон запроса удаления студента
     */
    private static final String DELETE_PERSON_SQL_TEMPLATE = "" +
            "DELETE FROM person WHERE person_id=?";

    private final Connection conn;

    /**
     * @param conn Коннектор к БД
     */
    public PersonDAOImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * Получить список всех студентов
     *
     * @return список всех студентов
     * @throws SQLException ошибка работы с БД
     */
    @Override
    public Collection<Person> getAllPersons() throws SQLException {
        LOGGER.debug("Получение списка предметов");
        List<Person> result = new ArrayList<>();
        try (Statement st = conn.createStatement()) {
            LOGGER.trace("users list {}", st);
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


    /**
     * Сохранить предмет в БД
     *
     * @param person объект-студент
     * @throws SQLException ошибка работы с БД
     */
    @Override
    public void createPerson(Person person) throws SQLException {
        LOGGER.debug("Создание пользователя {}", person);
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_PERSON_SQL_TEMPLATE)) {
            stmt.setString(1, person.getName());
            stmt.setDate(2, new Date(person.getBirthDate()));
            LOGGER.trace("user creation, {}", stmt);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                person.setId(res.getInt("person_id"));
            }
        }
    }

    /**
     * Изменить предмет в БД
     *
     * @param person объект-студент
     * @throws SQLException ошибка работы с БД
     */
    @Override
    public void updatePerson(Person person) throws SQLException {
        LOGGER.debug("Изменение пользователя {}", person);
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_PERSON_SQL_TEMPLATE)) {
            stmt.setString(1, person.getName());
            stmt.setDate(2, new Date(person.getBirthDate()));
            stmt.setInt(3, person.getId());
            LOGGER.trace("user edit {}", stmt);
            stmt.execute();
        }
    }

    /**
     * Удалить предмет из БД
     *
     * @param person объект-студент
     * @throws SQLException ошибка работы с БД
     */
    @Override
    public void deletePerson(Person person) throws SQLException {
        LOGGER.debug("Удаление пользователя {}", person);
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_PERSON_SQL_TEMPLATE)) {
            stmt.setInt(1, person.getId());
            LOGGER.trace("user deletion {}", stmt);
            stmt.execute();
        }
    }
}
