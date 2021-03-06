package basicKnowledge._Lock;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class _Bank_SynchronizedBlock {
    private final double[] accounts;
    private ReentrantLock lock=new ReentrantLock();
    public _Bank_SynchronizedBlock(int n,double initialBalance) {
        accounts=new double[n];
        Arrays.fill(accounts,initialBalance);

    }

    public int size(){
        return accounts.length;
    }

    public double getTotalBalance(){
        double sum=0;
        for(double a:accounts){
            sum+=a;
        }
        return sum;
    }
    public void transfer(int from,int to,double amount){
        if(accounts[from]<amount)return;

        synchronized (lock) {
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
        }

    }
}
