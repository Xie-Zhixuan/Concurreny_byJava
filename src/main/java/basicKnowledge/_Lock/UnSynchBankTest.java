package basicKnowledge._Lock;

public class UnSynchBankTest {
    public static final int COUNTS=100;
    public static final double INITIAL_BALANCE=1000;
    public static final double MAX_AMOUNT=1000;
    public static final int DELAY=1;

    public static void main(String[] args) {
        var bank=new Bank(COUNTS,INITIAL_BALANCE);

        for (int i = 0; i <COUNTS ; i++) {
            int fromAccount=i;

            Runnable r=()->{
                try{
                    while (true){
                        int toAccount= (int) (bank.size()*Math.random());
                        double amount=MAX_AMOUNT*Math.random();
                        bank.transfer(fromAccount,toAccount,amount);
/*                        if(bank.getBalanceFrom(fromAccount)<=amount)
                            //thread might be deactivated at this point
                            bank.transfer(fromAccount,toAccount,amount);*/

                        Thread.sleep((long) (DELAY*Math.random()));
                    }
                }catch (InterruptedException e){

                }
            };

            var t=new Thread(r);
            t.start();
        }
    }
}
//while delay=10 每当前一个操作进行完后统计总金额时，下一个线程开始操作进行减法，所以会暂时少一点，但总是正常的
//现将延迟调小，就容易出现问题了