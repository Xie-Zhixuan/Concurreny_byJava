package basicKnowledge._Lock;

import java.util.Arrays;

public class Bank {
    private final double[] accounts;

    public Bank(int n,double initialBalance) {
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

        System.out.print(Thread.currentThread());
        accounts[from]-=amount;
        System.out.printf(" %10.2f from %d to %d",amount,from,to);//%m.nf：输出共占m列，其中有n位小数，如数值宽度小于m左端补空格
        accounts[to]+=amount;
        System.out.printf(" Total Balance: %10.2f%n",getTotalBalance());
//        %n is portable across platforms \n is not.
//        \n对于基于Unix的系统来说是正确的换行符，但其他系统可能会使用不同的字符来表示行尾。特别是Windows系统使用\r\n和早期MacOS系统使用\r。

    }
}
