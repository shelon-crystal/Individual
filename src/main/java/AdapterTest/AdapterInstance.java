package AdapterTest;

/**
 * @author maxcs
 */
public class AdapterInstance extends AdapterForAB{

    /**
     *  使用抽象类继承的方式实现适配器
     */
    @Override
    public void a() {
        System.out.println("running a()");
    }

    @Override
    public void b() {
        System.out.println("running b()");
    }

}
