package practice;

import java.util.concurrent.locks.ReentrantLock;

public class SuperPrinter {
//    编写两个线程,一个线程打印1-52的整数，另一个线程打印字母A-Z。打印顺序为12A34B56C….5152Z。
//    即按照整数和字母的顺序从小到大打印，并且每打印两个整数后，打印一个字母，交替循环打印，直到打印到整数52和字母Z结束。

    int counter=1;
    synchronized void print123(int num){

        while (counter%3==0){
            try {
                this.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.print(num);
        counter++;
        this.notifyAll();
    }

    synchronized void printABC(char word){
        while (counter%3!=0){
            try {
                this.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(word);
        counter++;
        this.notifyAll();

    }


    public static void main(String[] args) {

        SuperPrinter printer=new SuperPrinter();
        new Thread(()-> {
            for (int i = 1; i <=52 ; i++) {
                printer.print123(i);
            }
        }).start();

        new Thread(()->{
            for (char i = 'A'; i <='Z' ; i++) {
                printer.printABC(i);
            }
        }).start();

    }

}
