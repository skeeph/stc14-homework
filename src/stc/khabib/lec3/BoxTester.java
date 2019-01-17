package stc.khabib.lec3;

public class BoxTester {
    public static void main(String[] args) {
        ObjectBox ob = new ObjectBox();
        ob.addObject(2);
        ob.addObject(null);
        System.out.println(ob.dump());
    }
}
