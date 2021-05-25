package Concurrent_Collection;

import java.util.Arrays;

public class why {
    public static void main(String[] args) {
//        int[] a=new int[10];
//        Arrays.fill(a,1);
//        System.out.println(Arrays.toString(a));

        int b=0;
        Runnable r=()->{
            while(true){

            }
        };//局部内部类可以访问局部变量；而匿名内部类即不指定名字的局部内部类，∵你需要他的一个对象
        Runnable r_=() -> {
            while (true) {
                System.out.println(b);
            }
        };

        for(int i=0;i<1;i++) {
            Thread t = new Thread(r);
            Thread t_ = new Thread(r_);
            t.start();
            t_.start();
        }




    }

}
