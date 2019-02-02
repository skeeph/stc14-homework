package khabib.lec12_dao.entity;

import java.util.Collection;
import java.util.Objects;

/**
 * Студент и список изучаемых им предметов
 */
public class CoursePerson {
    private Person person;
    private Collection<Subject> subjects;

    /**
     * @return Студент
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person Студент
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return Список предметов
     */
    public Collection<Subject> getSubjects() {
        return subjects;
    }

    /**
     * @param subjects Список предметов
     */
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
