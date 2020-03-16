package JUC_basic_code;

import java.util.concurrent.CountDownLatch;

/**
 * Author:liang;
 * Date:2020/3/16;
 * Time:12:10;
 * Package Name:JUC_basic_code;
 *
 * 需求：
 * 假设6个自定义线程，1个主线程。
 * 主线程必须等6个线程都执行完毕之后才能执行
 *
 *
 * CountDownLatch辅助类
 */
public class _9CountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        realCloseDoor();

    }

    /**
     * 使用CountDownLatch辅助类实现线程等待执行
     * @throws InterruptedException
     */
    private static void realCloseDoor() throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(6);//这个参数是次数
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"...关门走人");
                countDownLatch.countDown();//每次countDown,次数就减一
            },i+"").start();
        }
        countDownLatch.await();//除非次数减到0，否则就会阻塞
        System.out.println("班长关门走人");
    }

    private static void closeDoor() {
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"...关门走人");
            },i+"").start();
        }
        System.out.println("班长关门走人");
    }
}
