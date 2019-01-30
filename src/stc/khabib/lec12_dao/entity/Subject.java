package stc.khabib.lec12_dao.entity;

import java.util.Objects;

/**
 * Предмет
 */
public class Subject {
    private int id;
    private String description;

    /**
     * @return ID Предмета
     */
    public int getId() {
        return id;
    }

    /**
     * @param id ID Предмета
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Описание предмета
     */
    public String getDescription() {
        return description;
    }

    /**
     * Установка описания предмета
     *
     * @param description описание предмета
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Сравнение предметов
     * @param o другой предмет
     * @return равенство
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id == subject.id;
    }

    /**
     * @return хеш код
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * @return строковое представление
     */
    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
