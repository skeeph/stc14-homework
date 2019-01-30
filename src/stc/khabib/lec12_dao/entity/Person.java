package stc.khabib.lec12_dao.entity;

import java.util.Date;
import java.util.Objects;

/**
 * Студент
 */
public class Person {
    private int id;
    private String name;
    private long birthDate;

    /**
     * Конструктор по умолчанию
     */
    public Person() {
    }

    /**
     * Конструктор с параметрами
     *
     * @param name      Имя
     * @param birthDate дата рождения
     */
    public Person(String name, long birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    /**
     * @return ID Студента
     */
    public int getId() {
        return id;
    }

    /**
     * Установка ID
     * @param id ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Имя пользователя
     */
    public String getName() {
        return name;
    }

    /**
     * Установка имени пользователя
     * @param name имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Дата рождения
     */
    public long getBirthDate() {
        return birthDate;
    }

    /**
     * Установка даты рождения
     * @param birthDate Дата рождения
     */
    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + new Date(birthDate) +
                '}';
    }
}
