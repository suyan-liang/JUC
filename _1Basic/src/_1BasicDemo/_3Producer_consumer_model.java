package _1BasicDemo;

/**
 * Author:liang;
 * Date:2020/3/15;
 * Time:16:28;
 * Package Name:_1BasicDemo;
 * 需求：
 * 步骤：
 *
 */


/**
 * 生产者消费者模式
 * 即线程之间的通信
 *   判断→干活→通知
 *   判断必须用while，否则在进入判断后如果被切到别的线程有可能会出现虚假唤醒,要用while进行重复判断避免生产者重复生产或者消费者重复消费
 *
 * 案例：现有两个线程，可以操作初始值为零的一个变量，实现一个线程加1，一个线程减1，交替操作10次
 */

//资源类
class Source{
    private int number=0;

    public synchronized void incre() throws InterruptedException {
        while (number!=0)
        {
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName()+"..."+number);
        this.notifyAll();
    }
    public synchronized void decre() throws InterruptedException {
        while (number==0)
        {
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName()+"..."+number);
        this.notifyAll();
    }
}
public class _3Producer_consumer_model {
    public static void main(String[] args)throws Exception
    {
        Source s=new Source();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                try {
                    s.incre();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"生产者1").start();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
            try {
                s.decre();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }},"消费者1").start();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                try {
                    s.incre();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"生产者2").start();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                try {
                    s.decre();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }},"消费者2").start();
    }
}
