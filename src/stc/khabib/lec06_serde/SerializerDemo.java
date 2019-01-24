package stc.khabib.lec06_serde;

import stc.khabib.lec06_serde.entity.Human;

/**
 * Демонстрация работы сериализации и десериализации
 */
public class SerializerDemo {
    public static void main(String[] args) throws Exception {
        Human wife = new Human("Arven", "Undomiel", 2777, 1.95, 70, false);
        Human h = new Human("Aragorn", "Elessar", 87, 1.85, 70, true, wife);
        Serialization sz = new Serialization();
        sz.serialize(h, "./lotr.json");

        Human dessed = (Human) sz.deSerialize("./lotr.json");
        System.out.println(dessed);
        System.out.println(dessed.getSpouse());
    }

}
