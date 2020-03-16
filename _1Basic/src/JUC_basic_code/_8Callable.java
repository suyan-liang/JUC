package JUC_basic_code;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Author:liang;
 * Date:2020/3/16;
 * Time:10:53;
 * Package Name:JUC_basic_code;
 * 需求：
 *
 *
 * java多线程中，获得线程的第3种方法
 * 1.   extends Thread
 * 2.   implements Runnable
 * 3.   implements Callable<T>
 */


/**
 * Callable接口和Runnable接口的区别
 *
 * 1.   抛异常 vs 不抛
 * 2.   call  vs run
 * 3.   有返回值 vs 无返回值
 *
 *
 *
 *
 * Thread的构造方法传的只能是Runnable接口，我们现在的是Callable接口，怎么让Callable接口和Runnable接口搭上边?
 * 找Runnable接口的子接口，发现FutureTask接口
 * FutureTask接口是Runnable接口的子接口，而且其构造函数   FutureTask(Callable<V> callable)参数是Callable接口
 *
 *
 * 这就是多态的思想，只要参数是一个接口，那么它的所有子接口都可以传进来，流批流批
 *
 *
 *
 *
 * 为什么要Callable,它的优势在哪?
 * 一个例子
 * 比如A任务5s，B任务5s，C任务10s
 * 让main线程进行A,B任务 另起一个线程计算C任务
 * 这样一共需要时间10s，结果就是
 * main_res + another_res
 * 这样可以用Callable的返回值返回运算结果
 *
 *
 * Callable细节
 * 1.   get方法一般放在最后面，这是个阻塞式方法，要等待线程计算完毕返回结果，所以放在后面避免阻塞前面的main，否则就起不到效果了
 * 2.   同一个实现Callable接口的任务传入两个线程，只会执行一次
 */
class myThread implements Callable<Integer>
{


    @Override
    public Integer call() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(4); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("this is callable");
        return 1024;
    }
}
public class _8Callable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask=new FutureTask<>(new myThread());
        new Thread(futureTask,"A").start();
        System.out.println("main计算完成");
        System.out.println("another线程计算完成"+futureTask.get());

        new Thread(futureTask,"B").start();


    }
}
