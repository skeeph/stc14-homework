package stc.khabib.lec12_dao.entity;

import java.util.Collection;
import java.util.Objects;

/**
 * Предмет, с списком студентов, изучающих его
 */
public class CourseSubject {
    private Subject subject;
    private Collection<Person> students;

    /**
     * @return Предмет
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * @param subject Предмет
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * @return Список студентов, изучающих предмет
     */
    public Collection<Person> getStudents() {
        return students;
    }

    /**
     * @param students Список студентов, изучающих предмет
     */
    public void setStudents(Collection<Person> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseSubject that = (CourseSubject) o;
        return Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject);
    }
}
