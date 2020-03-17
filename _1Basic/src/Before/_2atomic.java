package Before;

/**
 * Author:liang;
 * Date:2020/3/17;
 * Time:14:48;
 * Package Name:Before;
 *
 *
 *
 *
 * 原子性：所谓原子性，就是操作不可分割，有点像同步synchronized，
 *        这些步骤的操作就是一个原子，不能做了一步然后去做别的了
 *
 *
 *
 *
 *
 * 1.   初始内存中的number值为0，此时A线程从内存中获取这个值，而后number++,打印
 *      在写回到内存之前,B线程读取内存中的值，而后number++，打印，而后A,B线程都写入内存，这时候就会打印相同的值
 *      这也是初学多线程时常见的不同步的现象
 * 2.   使用原子变量，在进行原子变量的操作时，效果上是完全同步的，即效果上写的时候别的线程无法写
 *      实现了synchronized的功能。具体实现方法就是CAS，CAS是调用计算机硬件资源实现的算法
 *      可以保证同步
 * 3.   CAS算法：
 *      1.  CAS是无锁的算法，当A线程正在进行写操作时，B来写了，此时根据CAS算法，B线程不会执行任何
 *          操作，这时候就需要我们进行while判断是否成功
 *      2.  CAS包含3个操作数，V-内存值，A-进行比较的值，B-要写入内存的值，如果V≠A，则不进行操作
 *      3.  CAS:我认为位置 V 应该包含值 A；如果包含该值，则将 B 放到这个位置；
 *          否则，不要更改该位置，只告诉我这个位置现在的值即可
 *          V是内存中的位置，可以理解为可以实时监测
 *
 * 4.   CAS缺点
 *          1. ABA问题
 *          2.循环时间长开销大
 *              自旋CAS（不成功，就一直循环执行，直到成功）如果长时间不成功，
 *              会给CPU带来非常大的执行开销
 *          3. 只能保证一个共享变量的原子操作
 *
 *
 * 5.   比较　　
 * 　　　　1、对于资源竞争较少（线程冲突较轻）的情况，使用synchronized同步锁进行线程阻塞
 *          和唤醒切换以及用户态内核态间的切换操作额外浪费消耗cpu资源；
 *          而CAS基于硬件实现，不需要进入内核，不需要切换线程，操作自旋几率较少，
 *          因此可以获得更高的性能。
 *
 * 　　　 2、对于资源竞争严重（线程冲突严重）的情况，CAS自旋的概率会比较大，从而浪费更多的CPU资源，
 *          效率低于synchronized。
 *
 * 　　　补充： synchronized在jdk1.6之后，已经改进优化。synchronized的底层实现主要依靠
 *      Lock-Free的队列，基本思路是自旋后阻塞，竞争切换后继续竞争锁，稍微牺牲了公平性，
 *      但获得了高吞吐量。在线程冲突较少的情况下，可以获得和CAS类似的性能；
 *      而线程冲突严重的情况下，性能远高于CAS
 */


import java.util.concurrent.atomic.AtomicInteger;

/**
 * 不同步演示资源类
 */
class old_source
{
    private int number=0;
    void incre()
    {
        number++;
        System.out.println("number="+number);
    }
}

/**
 * 使用atomic变量的同步的资源类
 *
 * 这个东西用的时候就是当做类来用，和integer差不多
 * i++就相当于getAndIncrement
 */
class new_source{
    private AtomicInteger number=new AtomicInteger(0);
    void incre()
    {
        number.getAndIncrement();
        System.out.println(number.get());
    }

}
public class _2atomic {
    public static void main(String[] args) {

    }

    /**
     * 使用juc.atomic包实现同步
     */
    private static void new_ipp() {
        new_source ns=new new_source();
        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                ns.incre();
            },i+"").start();
        }
    }

    /**
     * 直接i++不同步的演示
     */
    private static void old_ipp() {
        old_source os=new old_source();
        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                os.incre();
            }).start();
        }
    }
}
