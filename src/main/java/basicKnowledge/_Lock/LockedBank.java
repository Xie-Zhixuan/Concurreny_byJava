package basicKnowledge._Lock;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockedBank {
    private ReentrantLock bankLock;

    private Condition sufficientFunds;//一个锁对象可以有一个或多个相关联的条件对象

    private final double[] accounts;

    public LockedBank(int n,double initialBalance) {
        accounts=new double[n];
        Arrays.fill(accounts,initialBalance);

        bankLock=new ReentrantLock();
        sufficientFunds=bankLock.newCondition();
        }


    public int size(){
        return accounts.length;
    }

    public double getTotalBalance(){
        bankLock.lock();
        try {
            double sum = 0;
            for (double a : accounts) {
                sum += a;
            }
            return sum;
        }finally {
            bankLock.unlock();
        }
    }

    public void transfer(int from,int to,double amount){
        bankLock.lock();
        //保证任何时刻只有一个线程进入临界区；一旦一个线程锁定了锁对象，其他任何线程都无法通过lock语句；当其他线程调用lock时，他们会暂停，直到第一个线程释放这个锁对象。

//        if(accounts[from]<amount)return;
        try {
            while (accounts[from]<amount)sufficientFunds.await();//当前线程暂停，并放弃锁

            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);//%m.nf：输出共占m列，其中有n位小数，如数值宽度小于m左端补空格
            accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
//        %n is portable across platforms \n is not.
//        \n对于基于Unix的系统来说是正确的换行符，但其他系统可能会使用不同的字符来表示行尾。特别是Windows系统使用\r\n和早期MacOS系统使用\r。


            sufficientFunds.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            bankLock.unlock();
        }
    }
}
