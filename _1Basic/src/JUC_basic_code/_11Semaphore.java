package JUC_basic_code;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Author:liang;
 * Date:2020/3/16;
 * Time:12:50;
 * Package Name:JUC_basic_code;
 * 需求：比喻成抢车位
 *
 * 用于多个共享资源的互斥使用，用于对于并发线程的控制
 * 可以起到限流效果！！！！66666
 *
 * 当资源数为1的时候就可以实现synchronized的效果，而且更加丰富
 *
 */
public class _11Semaphore {
    public static void main(String[] args) {
        Semaphore semaphore=new Semaphore(3);//三个车位
        /*
         * 每个车都进入车位一次
         */
        for (int i = 0; i <6 ; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();//占用了一个资源
                    System.out.println(Thread.currentThread().getName()+"抢到了一个车位");
                    try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName()+"离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    semaphore.release();
                }
            },i+"").start();
        }
    }
}
