package basicKnowledge;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

public class _ForkJoin {
/*    Java 7开始引入了一种新的Fork/Join线程池，它可以执行一种特殊的任务：把一个大任务拆成多个小任务并行执行。

    我们举个例子：如果要计算一个超大数组的和，最简单的做法是用一个循环在一个线程内完成：

            ┌─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┐
            └─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┘
    还有一种方法，可以把数组拆成两部分，分别计算，最后加起来就是最终结果，这样可以用两个线程并行执行：

            ┌─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┐
            └─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┘
            ┌─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┐
            └─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┘
    如果拆成两部分还是很大，我们还可以继续拆，用4个线程并行执行：

            ┌─┬─┬─┬─┬─┬─┐
            └─┴─┴─┴─┴─┴─┘
            ┌─┬─┬─┬─┬─┬─┐
            └─┴─┴─┴─┴─┴─┘
            ┌─┬─┬─┬─┬─┬─┐
            └─┴─┴─┴─┴─┴─┘
            ┌─┬─┬─┬─┬─┬─┐
            └─┴─┴─┴─┴─┴─┘
    这就是Fork/Join任务的原理：判断一个任务是否足够小，如果是，直接计算，否则，就分拆成几个小任务分别计算。这个过程可以反复“裂变”成一系列小任务。    */

    public static void main(String[] args) {
        // 创建2000个随机数组成的数组:
        long[] array = new long[2000];
        long expectedSum = 0;

        long start=System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            array[i] = random();
            expectedSum += array[i];
        }
        long end=System.currentTimeMillis();
        System.out.println("Expected sum: " + expectedSum+", and cost "+(end-start));


        // fork/join:
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = ForkJoinPool.commonPool().invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
    }


    static Random random = new Random(0);

    static long random() {
        return random.nextInt(10000);
    }



//        过程，一个大的计算任务0~2000首先分裂为两个小任务0~1000和1000~2000，这两个小任务仍然太大，继续分裂为更小的0~500，500~1000，1000~1500，1500~2000，最后，计算结果被依次合并，得到最终结果。
//        因此，核心代码SumTask继承自RecursiveTask，在compute()方法中，关键是如何“分裂”出子任务并且提交子任务：

    @AllArgsConstructor
    private static class SumTask extends RecursiveTask<Long>{
            static final int THRESHOLD=100;
            long[] array;
            int start;
            int end;

            @Override
            protected Long compute() {

            if(end-start<=THRESHOLD) {
                return Arrays.stream(array).skip(start).limit(end-start).sum();
            }else {
                //有丝分裂
                int mid=(start+end)/2;
                SumTask sumTask1 = new SumTask(array,start,mid);
                SumTask sumTask2 = new SumTask(array,mid,end);

                //合并运行
                invokeAll(sumTask1, sumTask2);

                //获得结果
                Long subResult1 = sumTask1.join();
                Long subResult2 = sumTask2.join();

                //汇总
                return subResult1 + subResult2;
            }
            }
        }
    }

//    Fork/Join线程池在Java标准库中就有应用。
//            Java标准库提供的java.util.Arrays.parallelSort(array)可以进行并行排序，它的原理就是内部通过Fork/Join对大数组分拆进行并行排序，在多核CPU上就可以大大提高排序的速度。

/*
小结
        Fork/Join是一种基于“分治”的算法：通过分解任务，并行执行，最后合并结果得到最终结果。

        ForkJoinPool线程池可以把一个大任务分拆成小任务并行执行，任务类必须继承自RecursiveTask或RecursiveAction。

        使用Fork/Join模式可以进行并行计算以提高效率。*/
