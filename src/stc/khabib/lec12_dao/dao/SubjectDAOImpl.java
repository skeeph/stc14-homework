package stc.khabib.lec12_dao.dao;

import stc.khabib.lec12_dao.entity.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Реализация интерфейса управления предметами
 */
@SuppressWarnings("Duplicates")
public class SubjectDAOImpl implements SubjectDAO {
    /**
     * Шаблон запроса создания предмета
     */
    private static final String INSERT_SUBJECT_SQL_TEMPLATE =
            "INSERT INTO subject(description) VALUES (?)";
    /**
     * Шаблон запроса измененияпредмета
     */
    private static final String UPDATE_SUBJECT_SQL_TEMPLATE =
            "UPDATE subject set description=? WHERE subject_id=?";
    /**
     * Шаблон запроса удаления предмета
     */
    private static final String DELETE_SUBJECT_SQL_TEMPLATE =
            "DELETE FROM subject where subject_id=?";
    /**
     * Запрос для получения всех предметов
     */
    private static final String GET_ALL_SUBJECTS_SQL_TEMPLATE =
            "SELECT * FROM subject";
    private final Connection conn;

    /**
     * @param conn Коннектор к БД
     */
    public SubjectDAOImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * Получить список всех предметов
     *
     * @return список всех предметов
     * @throws SQLException ошибка работы с БД
     */
    @Override
    public Collection<Subject> getAllSubjects() throws SQLException {
        List<Subject> result = new ArrayList<>();
        try (Statement st = conn.createStatement()) {
            ResultSet res = st.executeQuery(GET_ALL_SUBJECTS_SQL_TEMPLATE);
            while (res.next()) {
                Subject p = new Subject();
                p.setId(res.getInt("subject_id"));
                p.setDescription(res.getString("description"));
                result.add(p);
            }
        }
        return result;
    }

    /**
     * Сохранить предмет в БД
     *
     * @param subject предмет
     * @throws SQLException ошибка работы с БД
     */
    @Override
    public void createSubject(Subject subject) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SUBJECT_SQL_TEMPLATE)) {
            stmt.setString(1, subject.getDescription());
            stmt.execute();
        }
    }

    /**
     * Изменить предмет в БД
     *
     * @param subject предмет
     * @throws SQLException ошибка работы с БД
     */
    @Override
    public void updateSubject(Subject subject) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_SUBJECT_SQL_TEMPLATE)) {
            stmt.setString(1, subject.getDescription());
            stmt.setInt(2, subject.getId());
            stmt.execute();
        }
    }

    /**
     * Удалить предмет из БД
     *
     * @param subject предмет
     * @throws SQLException ошибка работы с БД
     */
    @Override
    public void deleteSubject(Subject subject) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_SUBJECT_SQL_TEMPLATE)) {
            stmt.setInt(1, subject.getId());
            stmt.execute();
        }
    }
}
