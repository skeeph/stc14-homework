package stc.khabib.lec06_serde;

public class SerializerDemo {
    public static void main(String[] args) throws Exception {
        Human wife = new Human("Arven", "Undomiel", 2777, 1.95, 70, false);
        Human h = new Human("Aragorn", "Elessar", 87, 1.85, 70, true, wife);
        Deserializer sz = new Deserializer();
//        sz.serialize(h,"./me.json");

        Human dessed = (Human) sz.deSerialize("./me.json");
        System.out.println(dessed);
//        System.out.println(dessed.getSpouse());
    }

}
