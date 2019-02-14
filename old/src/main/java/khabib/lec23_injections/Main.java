package khabib.lec23_injections;

import khabib.lec23_injections.api.Handler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("mySimpleContext.xml");
        Handler handler = ctx.getBean("dataHandler", Handler.class);
        System.out.println("Результат: " +
                handler.process("Source", "Dest"));
    }
}
