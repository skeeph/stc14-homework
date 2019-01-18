package stc.khabib.lec3;

public class BoxTester {
    public static void main(String[] args) {
        ObjectBox<Object> ob = new ObjectBox<>(new Object[]{1, 2, 3});
        System.out.println(ob.hashCode());
        ob.addObject("string");
        ob.addObject(new BoxTester());
        ObjectBox<Object> o1 = ob;
        System.out.println(ob.equals(o1));
    }
}
