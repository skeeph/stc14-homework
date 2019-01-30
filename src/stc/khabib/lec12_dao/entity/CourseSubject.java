package stc.khabib.lec12_dao.entity;

import java.util.Collection;
import java.util.Objects;

public class CourseSubject {
    private Subject subject;
    private Collection<Person> students;

    public Subject getSubject() {
        return subject;
    }

    public CourseSubject setSubject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public Collection<Person> getStudents() {
        return students;
    }

    public CourseSubject setStudents(Collection<Person> students) {
        this.students = students;
        return this;
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
