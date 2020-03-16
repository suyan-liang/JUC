package JUC_basic_code;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author:liang;
 * Date:2020/3/15;
 * Time:14:14;
 * Package Name:JUC_basic_code;
 * 需求：卖3000张票
 * 步骤：
 */
class Ticket //资源类=实例变量+实例方法
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
 *  在高内聚低耦合的前提下   线程 + 操作 + 资源类
 */
public class _1SaleSocket {
    public static void main(String[] args) {
        Ticket ticket=new Ticket();
        new Thread(()->{ for (int i = 0; i <4000 ; i++) ticket.sale(); },"A").start();//根据这个参数类型，它默认我这是runnable接口
        new Thread(()->{ for (int i = 0; i <4000 ; i++) ticket.sale(); },"B").start();
        new Thread(()->{ for (int i = 0; i <4000 ; i++) ticket.sale(); },"C").start();
/*         new Thread(new Runnable() {
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
