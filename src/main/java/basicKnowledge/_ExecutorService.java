package basicKnowledge;

import lombok.AllArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class _ExecutorService {
//    Java标准库提供了ExecutorService接口表示线程池，

/*    因为ExecutorService只是接口，Java标准库提供的几个常用实现类有：
        FixedThreadPool：线程数固定的线程池；
        CachedThreadPool：线程数根据任务动态调整的线程池；
        SingleThreadExecutor：仅单线程执行的线程池。
    创建这些线程池的方法都被封装到Executors这个类中。
    */

    public static void main(String[] args) throws InterruptedException {
/*        ExecutorService pool= Executors.newFixedThreadPool(4);
        for (int i = 1; i <=6 ; i++) {
            pool.submit(new Task(Integer.toString(i)));
        }
        pool.shutdown();*/

//        如果我们把线程池改为CachedThreadPool，由于这个线程池的实现会根据任务数量动态调整线程池的大小，所以6个任务可一次性全部同时执行。
         ExecutorService pool= Executors.newCachedThreadPool();
        for (int i = 1; i <=6 ; i++) {
            pool.submit(new Task(Integer.toString(i)));
        }
        pool.shutdown();




//        ScheduledThreadPool
//        还有一种任务，需要定期反复执行，例如，每秒刷新证券价格。这种任务本身固定，需要反复执行的，可以使用ScheduledThreadPool。放入ScheduledThreadPool的任务可以定期反复执行。

        ScheduledExecutorService scheduledPool=Executors.newScheduledThreadPool(4);
        scheduledPool.schedule(new Task("one task"),1, TimeUnit.SECONDS);//一秒后执行一次性任务

        scheduledPool.scheduleAtFixedRate(new Task("3-day coming task"),2,3,TimeUnit.SECONDS);//二秒后，每三秒来
        scheduledPool.scheduleWithFixedDelay(new Task("3-day rest task"),2,3,TimeUnit.SECONDS);//二秒后，每三秒间隔

        Thread.sleep(10000);
        scheduledPool.shutdown();

/*        注意FixedRate和FixedDelay的区别。FixedRate是指任务总是以固定时间间隔触发，不管任务执行多长时间：

            │░░░░   │░░░░░░ │░░░    │░░░░░  │░░░
            ├───────┼───────┼───────┼───────┼────>
            │<─────>│<─────>│<─────>│<─────>│
        而FixedDelay是指，上一次任务执行完毕后，等待固定的时间间隔，再执行下一次任务：

        │░░░│       │░░░░░│       │░░│       │░
        └───┼───────┼─────┼───────┼──┼───────┼──>
            │<─────>│     │<─────>│  │<─────>│
*/




//        Java标准库还提供了一个java.util.Timer类，这个类也可以定期执行任务，但是，一个Timer会对应一个Thread，所以，一个Timer只能定期执行一个任务，多个定时任务必须启动多个Timer，
//        而一个ScheduledThreadPool就可以调度多个定时任务，所以，我们完全可以用ScheduledThreadPool取代旧的Timer。


/*        小结
        JDK提供了ExecutorService实现了线程池功能：

        线程池内部维护一组线程，可以高效执行大量小任务；

        Executors提供了静态方法创建不同类型的ExecutorService；

        必须调用shutdown()关闭ExecutorService；

        ScheduledThreadPool可以定期调度多个任务。*/
    }

    @AllArgsConstructor
    private static class Task implements Runnable{


        private final String name;

        @Override
        public void run() {
            System.out.println(name+": start");
            try {
                Thread.currentThread().sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(name+": end");

        }
    }

}
