package ThreadPool;

/**
 * Author:liang;
 * Date:2020/3/16;
 * Time:14:43;
 * Package Name:ThreadPool;
 * 需求：
 * 步骤：
 */

import java.util.concurrent.*;

import static java.util.concurrent.ThreadPoolExecutor.*;

/**
 * 线程池的好处
 * 1.   不用再new线程了，用时即取，减少了资源的消耗，优化了性能
 * 2.   特点 线程复用；控制最大并发数；管理线程
 *
 * 就是给你提供一个可以方便操纵线程的场所，线程任务你自己定，放进execute函数，它有空了自动帮你执行
 *
 *
 * 使用的时候是用的Executor的子接口ExecutorService
 * 利用Executors工具类创建线程池
 * 有fixed,single,cached三种类型，究其源码，都是使用的ThreadPoolExecutor,都是同一种类型
 * 底层是用到了阻塞队列
 *
 *     public static ExecutorService newFixedThreadPool(int nThreads) {
 *         return new ThreadPoolExecutor(nThreads, nThreads,
 *                                       0L, TimeUnit.MILLISECONDS,
 *                                       new LinkedBlockingQueue<Runnable>());
 *     }
 *
 *        public static ExecutorService newSingleThreadExecutor() {
 *         return new FinalizableDelegatedExecutorService
 *             (new ThreadPoolExecutor(1, 1,
 *                                     0L, TimeUnit.MILLISECONDS,
 *                                     new LinkedBlockingQueue<Runnable>()));
 *     }
 *
 *         public static ExecutorService newCachedThreadPool() {
 *         return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
 *                                       60L, TimeUnit.SECONDS,
 *                                       new SynchronousQueue<Runnable>());
 *     }
 *
 *
 * -------------------------------------------------------------------------------------------------
 * 这个是ThreadPoolExecutor的源码
 *     public ThreadPoolExecutor(int corePoolSize,
 *                               int maximumPoolSize,
 *                               long keepAliveTime,
 *                               TimeUnit unit,
 *                               BlockingQueue<Runnable> workQueue) {
 *         this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
 *              Executors.defaultThreadFactory(), defaultHandler);
 *     }
 * ------------------------------------------------------------------------------------------------
 *  线程池七大参数
 *  1.  corePoolSize 线程池中常驻核心线程数量
 *  2.  maximumPoolSize 线程池中能够同时容纳同时执行的最大线程数，必须大于等于1
 *  3.  keepAliveTime 多余的空闲线程的存活时间，当线程数量大于corePoolSize时，空闲时间达到该值，多余线程会被销毁
 *  4.  unit  keepAliveTime的单位
 *  5.  workQueue  任务队列，被提交但尚未被执行的任务
 *  6.  threadFactory 生成线程池的线程工程，用于创建线程，一般用默认的
 *  7.  handle 拒绝策略，当队列满了，且线程达到maximumPoolSize时如何拒绝请求的Runnable任务的策略
 *
 *
 *
 *
 *  --------------------------------------------------------------------------------------------------
 *  线程池的工作原理
 *
 *
 * 用银行的Demo可以很容易理解
 *  使用者提交任务
 *  线程池判断
 *   核心线程池是否满?否--创建线程任务
 *   是---进入阻塞队列（是否已满）?否，进里面呆着
 *                               是，把工作线程数括到maximumPoolSize，队列的客人来这，新来的去队列（是否已满）
 *                                                                            是，按照拒绝测量拒绝新提交的任务
 * ---------------------------------------------------------------------------------------------------
 * 在实际工作中，不允许使用Executors创建线程池，必须通过ThreadPoolExecutor的方式去创建
 * 弊端如下
 * Fixed和single允许的请求队列的长度为max_value，可能会堆积大量请求，导致OOM
 * cached和scheduled允许创建线程的数量为max_value，可能会创建大量线程，导致OOM
 *
 *
 * ------------------------------------------------------------------------------------------------------
 * 拒绝策略
 *
 * 1.   AbortPolicy(默认) 直接抛出RejectedExecutionException异常阻止系统正常运行
 * 2.   CallerRunsPolicy  不会直接拒绝任务，也不会抛出异常，而是把任务返还给提交的线程
 * 3.   DiscardPolicy     直接丢弃任务，不会抛出异常
 * 4.   DiscardOldestPolicy  把队列中等待时间最久的抛弃，然后把当前任务加入队列尝试再次提交
 *
 *
 *
 * maximumPoolSize
 * 1.   CPU密集型，核数+1   Runtime.getRuntime().availableProcessors()+1
 * 2.   IO密集型  核数/阻塞系数
 */
public class _2threadpool {
    public static void main(String[] args) {
        //线程池的最大容纳量  maximumPoolSize+队列的capacity
        //假设核心参数2，最大5，候客区3,消亡1s,默认工厂,abort策略

        ExecutorService threadpool=new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );


        for (int i = 0; i <15 ; i++) {
           // try {TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            threadpool.execute(()->{
                System.out.println(Thread.currentThread().getName()+"号业务员正在办理业务");
            });
        }
        threadpool.shutdown();

    }

    /**
     * 使用Executors创建的线程池
     */
    private static void initPool() {
        //fixed是开辟有固定数量线程的线程池，现在相当于有5个银行工作人员
        //ExecutorService threadpool= Executors.newFixedThreadPool(5);


        //single是开辟只有一个线程的线程池，相当于有1个银行工作人员
        //ExecutorService threadpool = Executors.newSingleThreadExecutor();


        //cached是开辟有n个线程的线程池，可以视情况不同进行自动扩容，灵活性更强！
        //完全看顾客的情况，就和真的银行差不多
        ExecutorService threadpool = Executors.newCachedThreadPool();

        //这里相当于10名顾客，在execute中传入执行任务，交给线程去执行
        for (int i = 0; i <10 ; i++) {
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            threadpool.execute(()->{
                System.out.println(Thread.currentThread().getName()+"正在办理业务.....");

                System.out.println(Thread.currentThread().getName()+"办理完毕！！！！！");
            });
        }
        //线程池用完后，要用shutdown关闭
        threadpool.shutdown();
    }
}
