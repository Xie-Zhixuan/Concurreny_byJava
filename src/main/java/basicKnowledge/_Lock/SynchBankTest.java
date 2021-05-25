package basicKnowledge._Lock;

public class SynchBankTest {
    public static final int COUNTS=100;
    public static final double INITIAL_BALANCE=1000;
    public static final double MAX_AMOUNT=1000;
    public static final int DELAY=1;

    public static void main(String[] args) {
        var bank=new LockedBank(COUNTS,INITIAL_BALANCE);

        for (int i = 0; i <COUNTS ; i++) {
            int fromAccount=i;

            Runnable r=()->{
                try{
                    while (true){
                        int toAccount= (int) (bank.size()*Math.random());
                        double amount=MAX_AMOUNT*Math.random();
                        bank.transfer(fromAccount,toAccount,amount);
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
