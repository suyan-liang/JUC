package JUC_code;

/**
 * Author:liang;
 * Date:2020/3/15;
 * Time:17:27;
 * Package Name:JUC_code;
 * 需求：使用lock锁的生产者消费者版本
 *
 *
 * 步骤：
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock  替代  synchronized
 * Condition 替代 Object监视器
 * await 替代  wait
 * signal 替代 notify
 *
 *
 * 多线程三句话
 * 1.线程操作资源类
 * 2.通知干活唤醒
 * 3.通知必须用while
 */

class mySource
{
    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();
    private int number=0;
    public void incre()
    {
        lock.lock();
        try{
            while(number!=0)
            {
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+"..."+number);
            condition.signalAll();
        }catch(Exception e)
        {
              e.printStackTrace();
        }
         finally {
              lock.unlock();
         }
    }
    public void decre()
    {
        lock.lock();
        try{
            while(number==0)
            {
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+"..."+number);
            condition.signalAll();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
}
public class _4new_version {
    public static void main(String[] args) {
        mySource s=new mySource();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                s.incre();
            }
        },"生产1").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                s.incre();
            }
        },"生产2").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                s.decre();
            }
        },"消费1").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                s.decre();
            }
        },"消费2").start();
    }
}
