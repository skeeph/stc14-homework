package stc.khabib.lec12_dao.dao;

import stc.khabib.lec12_dao.entity.Subject;

import java.sql.SQLException;
import java.util.Collection;

public interface SubjectDAO {
    Collection<Subject> getAllSubjects() throws SQLException;

    void createSubject(Subject subject) throws SQLException;

    void updateSubject(Subject subject) throws SQLException;

    void deleteSubject(Subject subject) throws SQLException;
}
