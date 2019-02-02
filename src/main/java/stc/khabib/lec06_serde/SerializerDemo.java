package stc.khabib.lec06_serde;

import stc.khabib.lec06_serde.entity.Human;

/**
 * Демонстрация работы сериализации и десериализации
 */
public class SerializerDemo {
    public static void main(String[] args) throws Exception {
        Serialization sz = new Serialization();
        Object o = sz.deSerialize("./vasya.json");
        Human h = (Human) o;
        System.out.println(h.toString());
    }

}
