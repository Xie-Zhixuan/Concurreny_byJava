package basicKnowledge;

public class _Thread {
//    Java语言内置了多线程支持。当Java程序启动的时候，实际上是启动了一个JVM进程
//    ，然后，JVM启动主线程来执行main()方法。在main()方法中，我们又可以启动其他线程。
    public static void main(String[] args) throws InterruptedException {

        System.out.println("main start...");

        var t1=new Thread(()->{
            System.out.println("hello,world");
        });
        t1.start();//当线程的run方法执行完方法体中最后一条语句，or 出现没有捕获的异常时，线程终止

//        try{
//            Thread.sleep(20);
//        }catch (InterruptedException e){
//            //不妨让主线程即main方法放水一下。
//        }

//        一个线程还可以等待另一个线程直到其运行结束。例如，main线程在启动t线程后，可以通过t.join()等待t线程结束后再继续运行：
        t1.join();
//        此外，join(long)的重载方法也可以指定一个等待时间，超过等待时间后就不再继续等待。


        System.out.println("main end...");
    }
//    除了可以肯定，main start会先打印外，main end打印在thread run之前、thread end之后或者之间，都无法确定。
//    因为从t线程开始运行以后，两个线程就开始同时运行了，并且由操作系统调度，程序本身无法确定线程的调度顺序。


}
/*
要特别注意：直接调用Thread实例的run()方法是无效的：

public class Main {
    public static void main(String[] args) {
        Thread t = new MyThread();
        t.run();
    }
}

class MyThread extends Thread {
    public void run() {
        System.out.println("hello");
    }
}
    直接调用run()方法，相当于调用了一个普通的Java方法，当前线程并没有任何改变，也不会启动新线程。上述代码实际上是在main()方法内部又调用了run()方法，打印hello语句是在main线程中执行的，没有任何新线程被创建。
必须调用Thread实例的start()方法才能启动新线程，如果我们查看Thread类的源代码，会看到start()方法内部调用了一个private native void start0()方法，native修饰符表示这个方法是由JVM虚拟机内部的C代码实现的，不是由Java代码实现的。
*/




/*
线程的优先级
        可以对线程设定优先级，设定优先级的方法是：

        Thread.setPriority(int n) // 1~10, 默认值5
        优先级高的线程被操作系统调度的优先级较高，操作系统对高优先级线程可能调度更频繁，但我们决不能通过设置优先级来确保高优先级的线程一定会先执行。
*/
