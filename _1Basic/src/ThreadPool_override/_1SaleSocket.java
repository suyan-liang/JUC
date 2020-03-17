package ThreadPool_override;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author:liang;
 * Date:2020/3/17;
 * Time:12:22;
 * Package Name:ThreadPool_override;
 * 需求：用线程池重写卖票问题
 * 步骤：
 */

class Ticket
{
    private Lock lock=new ReentrantLock();
    private int number=30;
    public void sale()
    {
        lock.lock();
        try{
            if (number>0)
            {
                System.out.println(Thread.currentThread().getName()+"卖出第"+(number--)+"，剩余"+number);
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
public class _1SaleSocket {
    public static void main(String[] args) {
        Ticket ticket=new Ticket();
        ExecutorService threadpool=new ThreadPoolExecutor(
          3,5,1L, TimeUnit.SECONDS,
          new LinkedBlockingDeque<>(3),Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy()
        );
        for (int i = 0; i <100 ; i++) {
            threadpool.execute(()->{
                ticket.sale();
            });
        }
    }
}
