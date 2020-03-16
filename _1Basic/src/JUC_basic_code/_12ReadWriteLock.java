package JUC_basic_code;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Author:liang;
 * Date:2020/3/16;
 * Time:13:07;
 * Package Name:JUC_basic_code;
 * 需求：
 *
 *
 * 读写锁
 * 读写分离
 * 多个线程同时读一个资源不会出现问题，为了满足并发，读取共享资源可以同时进行
 * 但是如果有一个线程想去写资源，就不应该有其他线程去读或者写。这还和CopyOnWriteArrayList不太一样
 *
 * 读读可以共存
 * 读写、写写不能共存
 *
 *
 *
 * 读写锁  ReadWriteLock  实现类  ReentrantReadWriteLock
 * 又分读锁和写锁
 * 见如下使用
 */
class myCache
{
    private volatile Map<String,Object> map=new HashMap<>();
    private ReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    public void put(String key,Object value)
    {





        readWriteLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"开始写入数据");
            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"写入完成！");
        }catch(Exception e)
        {
              e.printStackTrace();
        }
         finally {
              readWriteLock.writeLock().unlock();
         }

    }
    public void get(String key)
    {

        readWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"-----------------开始读取数据");
            try {TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"读取完成！-------------"+map.get(key));
        }catch(Exception e)
        {
              e.printStackTrace();
        }
         finally {
              readWriteLock.readLock().unlock();
         }

    }
}
public class _12ReadWriteLock {
    public static void main(String[] args) {
        myCache cache=new myCache();
        for (int i = 0; i <5 ; i++) {
            final int tempInt=i;
            new Thread(()->{
                cache.put(tempInt+"",tempInt+"");
            },i+"").start();
        }
        for (int i = 0; i <5 ; i++) {
            final int tempInt=i;
            new Thread(()->{
                cache.get(tempInt+"");
            },i+"").start();
        }
    }
}
