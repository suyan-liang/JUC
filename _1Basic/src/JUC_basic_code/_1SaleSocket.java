package JUC_basic_code;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 资源类=变量（资源）+实例方法（操纵资源的方法）+并发保护（锁）
 *
 * 如果是生产者消费者模式，且使用notifyAll，必须要使用while进行判断，否则可能产生虚假唤醒
 * 这里是线程之间不进行互相通信，相当于多个消费者瓜分资源，没有生产者，无需使用while循环判断
 *
 * 所谓虚假唤醒，可以形象地理解为 “队友太6”，前提是消费者生产者且多个，且notifyAll
 * 比如A生产者进入判断，看到还有资源，就睡了，此时消费者过来把资源消费了，唤醒所有生产者
 * 结果B生产了资源，A如果是if判断，就会也去生产资源，造成数据错误，虚假唤醒
 * 用while就可以避免这种情况。
 *
 *
 */
class myTickets
{
    private int number=3000;
    //ReentrantLock 是 可重用锁
    Lock lock=new ReentrantLock();
    public void sale()
    {
        lock.lock();
        try{
            if(number>0) {
                System.out.println(Thread.currentThread().getName() + "卖出了第" + (number--) + "张票，现在还剩" + number);
            }
        }catch(Exception e)
        {
              e.printStackTrace();
        }
         finally {
              lock.unlock();
         }
    }

}

/**
 *  在高内聚低耦合的前提下
 *  线程 + 操作 + 资源
 *  这个操作其实就是线程任务，怎么通过调用资源的函数来操作资源
 */
public class _1SaleSocket {
    public static void main(String[] args) {
        myTickets ticket=new myTickets();
        new Thread(()->{ for (int i = 0; i <4000 ; i++) ticket.sale(); },"A").start();//根据这个参数类型，它默认我这是runnable接口
        new Thread(()->{ for (int i = 0; i <4000 ; i++) ticket.sale(); },"B").start();
        new Thread(()->{ for (int i = 0; i <4000 ; i++) ticket.sale(); },"C").start();
        /* new Thread(new Runnable() {
           @Override
           public void run() {
               for (int i = 0; i < 4000; i++) {
                   ticket.sale();
               }
           }
       },"A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 4000; i++) {
                    ticket.sale();
                }
            }
        },"B").start();*/

    }
}
