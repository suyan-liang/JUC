package JUC_basic_code;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Author:liang;
 * Date:2020/3/16;
 * Time:12:33;
 * Package Name:JUC_basic_code;
 *
 *
 *
 *
 * 这个东西是要等待指定个数线程await()，才能执行定义的那个线程任务
 * CyclicBarrier(int parties, Runnable barrierAction)
 *
 *
 * 至于区别，真挺像的，难搞哦
 */
public class _10CyclicBarrier {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(7,()->{
            System.out.println("召唤神龙");
        });


        for (int i = 1; i <= 7; i++) {
            final int tempInt=i;//在lambda表达式中用变量需要final型
            new Thread(()->{
                System.out.println("这是"+tempInt+"星珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            },tempInt+"").start();

        }
    }
}
