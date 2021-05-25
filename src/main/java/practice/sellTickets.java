package practice;

public class sellTickets {

    public static void main(String[] args) {
        TicketRepository warehouse = new TicketRepository(100);
        new Thread(new ticketOffice(warehouse,11), "售票点1").start();
        new Thread(new ticketOffice(warehouse, 22), "售票点2").start();
        new Thread(new ticketOffice(warehouse,33), "售票点3").start();
        new Thread(new ticketOffice(warehouse, 19), "售票点4").start();
        new Thread(new ticketOffice(warehouse,21), "售票点5").start();
    }


    //票仓
    private static class TicketRepository {
        public int available;//总票数

        public TicketRepository(int available) {
            this.available = available;
        }


        public boolean ifCould(int ticketBuy) {//
            if (ticketBuy > available) {
                return false;
            } else {
                available -= ticketBuy;
                return true;
            }
        }
    }

    //代售点
    private static class ticketOffice implements Runnable {

        int ticketBuy;//想购买的票数
        TicketRepository from;

        public ticketOffice( TicketRepository from,int ticketBuy) {
            this.from=from;
            this.ticketBuy = ticketBuy;

        }

        @Override
        public void run() {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            synchronized (sellTickets.class) {
                boolean flag = from.ifCould(ticketBuy);
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + "想要购票" + ticketBuy + "---------->购票成功 剩余" + from.available);
                } else {
                    System.out.println(Thread.currentThread().getName() + "想要购票" + ticketBuy + "---------->失败");
                }
            }

        }


    }
}