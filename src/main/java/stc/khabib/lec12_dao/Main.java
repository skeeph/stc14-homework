package stc.khabib.lec12_dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stc.khabib.lec12_dao.dao.PersonDAO;
import stc.khabib.lec12_dao.dao.PersonDAOImpl;
import stc.khabib.lec12_dao.dao.SubjectDAO;
import stc.khabib.lec12_dao.entity.Person;
import stc.khabib.lec12_dao.entity.Subject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Запуск программы");
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String login = "inno";
        String pass = "polis";

        try (Connection conn = DriverManager.getConnection(url, login, pass)) {
            PersonDAO dao = new PersonDAOImpl(conn);
            LOGGER.info("Подключение к БД выполнено");
            createPersons(dao);
        } catch (SQLException e) {
            LOGGER.error("Ошибка в БД", e);
        }

    }

    public static void createPersons(PersonDAO dao) throws SQLException {
        String[] names = new String[]{
                "Arya", "Sansa", "Bran"
        };
        for (String name : names) {
            Person person = new Person();
            person.setName(name);
            person.setBirthDate(System.currentTimeMillis());
            dao.createPerson(person);
            LOGGER.info("Created: {}", person);
        }
    }

    public static void createSubjects(SubjectDAO dao) throws SQLException {
        String[] names = new String[]{
                "FaceLess", "Purple wedding", "Three eye raven"
        };
        for (String name : names) {
            Subject subj = new Subject();
            subj.setDescription(name);
            dao.createSubject(subj);
            LOGGER.info("Created: {}", subj);
        }


    }
}
