package practice;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class printABC {
    private static Lock lock=new ReentrantLock();
    public static void main(String[] args) {
        new Thread(new Printer("A")).start();
        new Thread(new Printer("B")).start();
        new Thread(new Printer("C")).start();
        new Thread(new Printer("D")).start();
        new Thread(new Printer("E")).start();
    }

    @RequiredArgsConstructor
    private static class Printer implements Runnable{
        @NonNull
        String word;

        @Override
        public  void run() {
            try{
/*                synchronized (printABC.class) {
                    System.out.print(Thread.currentThread().getName());
                    for (int i = 0; i < 5; i++) {
                        System.out.print(word);
                        Thread.currentThread().sleep(10);
                    }
                    System.out.println();
                }*/

                lock.lock();

                    System.out.print(Thread.currentThread().getName());
                    for (int i = 0; i < 5; i++) {
                        System.out.print(word);
                        Thread.currentThread().sleep(10);
                    }
                    System.out.println();

            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }


        }
    }
}
