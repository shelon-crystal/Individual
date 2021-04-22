package AdapterTest;

/**
 * @author maxcs
 */
public class MainTest {

    public static void main(String[] args) {
//        AdapterTest.A obj = new AdapterTest.AdapterForAB(new AdapterTest.BImplement());
//        obj.aMethod();

        // 抽象类继承与实现的方式
        A a = new AdapterInstance();
        a.a();
        a.b();
    }
}
