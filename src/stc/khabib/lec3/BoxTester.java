package stc.khabib.lec3;

public class BoxTester {
    public static void main(String[] args) {
        ObjectBox ob = new ObjectBox();
        System.out.println(ob.addObject(1));
        System.out.println(ob.addObject("string"));
        System.out.println(ob.addObject(null));
        System.out.println(ob.addObject(null));
        System.out.println(ob);
        System.out.println(ob.hashCode());

        System.out.println();
        System.out.println(ob.deleteObject(null));
        System.out.println(ob.deleteObject(null));
        System.out.println(ob.deleteObject(1));
        System.out.println(ob);
        System.out.println(ob.hashCode());


        System.out.println("===========");
        ObjectBox ob1 = new ObjectBox(new Object[]{1, 2, 3});
        ObjectBox ob2 = new ObjectBox(new Object[]{1, 2, 3});

        System.out.println(ob1);
        System.out.println(ob2);
        System.out.println(ob1.equals(ob2));
        System.out.println(ob1.hashCode() == ob2.hashCode());

        System.out.println("============Add null==============");
        System.out.println(ob1.addObject(null));
        System.out.println(ob1);
        System.out.println(ob2);
        System.out.println(ob1.equals(ob2));
        System.out.println(ob1.hashCode() == ob2.hashCode());


        MathBox<Integer> mb = new MathBox<>(new Integer[]{1, 2, 3});
        mb.addObject("sss");

    }
}
