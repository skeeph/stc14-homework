package stc.khabib.lec06_serde;

public class SerializerDemo {
    public static void main(String[] args) throws IllegalAccessException {
        Human wife = new Human("Arven", "Undomiel", 2777, 1.95, 70, false);
        Human h = new Human("Aragorn", "Elessar", 87, 1.85, 70, true, wife);
        Serializer sz = new Serializer();
        sz.serialize(h, "./me.yml");
    }

}
