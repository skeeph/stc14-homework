package stc.khabib.lec12_dao.dao;


import stc.khabib.lec12_dao.entity.Person;
import stc.khabib.lec12_dao.entity.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("Duplicates")
public class CourseDAOImpl implements CourseDAO {
    private static final String LINK_PERSON_TO_SUBJECT_SQL_TEMPLATE =
            "INSERT INTO course(person_id, subject_id) VALUES (?,?);";
    private static final String GET_PERSONS_BY_SUBJECT_SQL_TEMPLATE =
            "SELECT DISTINCT course.person_id, name,birthdate FROM course " +
                    "LEFT JOIN person ON course.person_id = person.person_id " +
                    "WHERE subject_id = ?;";
    private static final String GET_SUBJECTS_BY_PERSON_SQL_TEMPLATE =
            "SELECT DISTINCT s.subject_id,description FROM course " +
                    "LEFT JOIN subject s on course.subject_id = s.subject_id " +
                    "WHERE person_id = ?;";
    private final Connection conn;

    public CourseDAOImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * Получить список всех студентов, изучающих данный предмет
     *
     * @param subject предмет
     * @return список студентов
     * @throws SQLException ошибка работы с БД
     */
    @Override
    public Collection<Person> getPersonsBySubject(Subject subject) throws SQLException {
        List<Person> result = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(GET_PERSONS_BY_SUBJECT_SQL_TEMPLATE)) {
            st.setInt(1, subject.getId());
            ResultSet res = st.executeQuery();
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
     * Получить список предметов, изучаемых данным студентом
     *
     * @param person студент
     * @return список предметов
     * @throws SQLException ошибка работы с БД
     */
    @Override
    public Collection<Subject> getSubjectsByPerson(Person person) throws SQLException {
        List<Subject> result = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(GET_SUBJECTS_BY_PERSON_SQL_TEMPLATE)) {
            st.setInt(1, person.getId());
            ResultSet res = st.executeQuery();
            while (res.next()) {
                Subject s = new Subject();
                s.setId(res.getInt("subject_id"));
                s.setDescription(res.getString("description"));
                result.add(s);
            }
        }
        return result;
    }

    /**
     * Записать данного студента, на несколько предметов
     *
     * @param person   студент
     * @param subjects список предметов
     * @throws SQLException ошибка работы с БД
     */
    @Override
    public void linkToCourse(Person person, Subject... subjects) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(LINK_PERSON_TO_SUBJECT_SQL_TEMPLATE)) {
            for (Subject subj : subjects) {
                stmt.setInt(1, person.getId());
                stmt.setInt(2, subj.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    /**
     * Записать на данный предмет нескольких студентов
     *
     * @param subject предмет
     * @param persons список студентов
     * @throws SQLException Ошибка работы с БД
     */
    @Override
    public void linkToCourse(Subject subject, Person... persons) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(LINK_PERSON_TO_SUBJECT_SQL_TEMPLATE)) {
            for (Person p : persons) {
                stmt.setInt(1, p.getId());
                stmt.setInt(2, subject.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
}
