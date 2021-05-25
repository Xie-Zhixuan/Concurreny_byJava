package basicKnowledge;

public class _ThreadLocal {
//即线程的局部变量
    //∵在线程间共享变量的风险，所以有时可避免共享变量。

    //使用ThreadLocal辅助类为每个线程提供各自的实例



    public static void main(String[] args) throws InterruptedException {
    new _ThreadLocal().getClass().wait();
        System.out.println("main end");
    }
}
