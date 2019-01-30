package stc.khabib.lec12_dao.entity;

import java.util.Collection;
import java.util.Objects;

public class CoursePerson {
    private Person person;
    private Collection<Subject> subjects;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Collection<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Collection<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoursePerson that = (CoursePerson) o;
        return Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person);
    }

    @Override
    public String toString() {
        return "CoursePerson{" +
                "person=" + person +
                ", subjects=" + subjects +
                '}';
    }
}
