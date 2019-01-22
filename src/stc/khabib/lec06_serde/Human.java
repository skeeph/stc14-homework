package stc.khabib.lec06_serde;

import java.util.Arrays;

public class Human {
    String firstName, lastName;
    int age;
    double height, weight;
    private boolean gender;
    private String[] nicks;
    private Human spouse;

    public Human() {

    }

    public Human(String firstName, String lastName, int age, double height, double weight, boolean gender, Human spouse) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.spouse = spouse;
    }

    public Human(String firstName, String lastName, int age, double height, double weight, boolean gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.nicks = new String[]{"this", "is", "some", "nick"};
    }

    @Override
    public String toString() {
        return "Human{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", gender=" + gender +
                ", nicks=" + Arrays.toString(nicks) +
                ", spouse=" + spouse +
                '}';
    }

    public Human getSpouse() {
        return spouse;
    }
}
