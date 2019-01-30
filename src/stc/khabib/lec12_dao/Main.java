package stc.khabib.lec12_dao;


import stc.khabib.lec12_dao.dao.PersonDAO;
import stc.khabib.lec12_dao.dao.PersonDAOImpl;
import stc.khabib.lec12_dao.dao.SubjectDAO;
import stc.khabib.lec12_dao.entity.Person;
import stc.khabib.lec12_dao.entity.Subject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String login = "inno";
        String pass = "polis";

        try (Connection conn = DriverManager.getConnection(url, login, pass)) {
            PersonDAO dao = new PersonDAOImpl(conn);
            createPersons(dao);
        }

    }

    public static void createPersons(PersonDAO dao) throws SQLException {
        // Create Persons
        Person person = new Person();
        person.setName("John Snow");
        person.setBirthDate(System.currentTimeMillis());
        person.setName("John Snow Targarien");
        dao.createPerson(person);
        System.out.println(person);

        Person ned = new Person("Ned Stark", System.currentTimeMillis() - 3600 * 1000 * 24);
        dao.createPerson(ned);
        System.out.println(ned);

        Person cate = new Person("Catelyn Tully", System.currentTimeMillis() - 3600 * 1000 * 24 * 2);
        dao.createPerson(cate);
        System.out.println(cate);
    }

    public static void createSubjects(SubjectDAO dao) throws SQLException {
        Subject subj = new Subject();
        subj.setDescription("Game of Thrones");
        dao.createSubject(subj);
        System.out.println(subj);

        Subject subj2 = new Subject();
        subj2.setDescription("War of five kings");
        dao.createSubject(subj2);
        System.out.println(subj2);

        Subject subj3 = new Subject();
        subj3.setDescription("Dance of the dragons");
        dao.createSubject(subj3);
        System.out.println(subj3);

    }
}
