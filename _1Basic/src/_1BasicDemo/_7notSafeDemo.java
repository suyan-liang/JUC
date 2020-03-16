package _1BasicDemo;

import org.w3c.dom.ls.LSException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Author:liang;
 * Date:2020/3/16;
 * Time:8:33;
 * Package Name:_1BasicDemo;
 * 需求：举例说明集合类是不安全的
 *
 *
 *
 * 1.   故障现象：java.util.ConcurrentModificationException  并发修改异常
 * 2.   导致原因：ArrayList线程不安全
 * 3.   解决方案：
 *          1.Vector                           性能比较慢
 *          2.Collections.synchronizedList()  工具类，传入一个List，返回一个线程安全的List  数据量小的时候好使
 *          3.CopyOnWriteArrayList            JUC的线程安全的容器，使用lock锁
 *                                            这个容器读的时候没有加lock锁，在写以及修改的时候加了lock锁
 *                                            写的时候只能一个人写，读的时候可以多个人读
 *
 *
 * 4.CopyOnWriteArrayList 源码分析
 *      add的时候加了lock锁，把原来的版本利用Arrays.copyof()方法复制了一份，添加上新的元素后，更新数组，而后解锁
 *      写时复制容器，好处是可以进行并发的读，而无需加锁，读写分离
 *
 *    public boolean add(E e) {
 *         final ReentrantLock lock = this.lock;
 *         lock.lock();
 *         try {
 *             Object[] elements = getArray();
 *             int len = elements.length;
 *             Object[] newElements = Arrays.copyOf(elements, len + 1);
 *             newElements[len] = e;
 *             setArray(newElements);
 *             return true;
 *         } finally {
 *             lock.unlock();
 *         }
 *     }
 *
 */
public class _7notSafeDemo {
    public static void main(String[] args) {
        MapNotSafe();
    }

    /**
     * 1.   HashMap的底层  Node<K,V> 类型的数组+单向链表+红黑树
     *       当单向链表长度到达8之后会转为红黑树
     * 2.   负载因子是反映哈希表装满程度的参数，α= 填入表中的元素个数 / 哈希表的长度，α越大，冲突概率越大
     *      当HashMap中的数据达到 表长*α 后进行扩容，扩容一倍
     *      ArrayList扩容一半
     *
     *      通过设置HashMap的初始容量来避免扩容时的时间
     *        public HashMap(int initialCapacity) {
     *         this(initialCapacity, DEFAULT_LOAD_FACTOR);
     *     }
     * 3.   改名啦！！！ConcurrentHashMap();
     */
    private static void MapNotSafe() {
        //Map<String,String> map=new HashMap<>();
        //Map<String,String> map=Collections.synchronizedMap(new HashMap<>());
        Map<String,String> map=new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                //这个东西是用来生成随机的字符串的
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, i + "").start();
        }
    }

    /**
     * 1.      HashSet的底层是HashMap,键就是set中的元素，值是PRESENT如下，一个固定的值
     * 2.      private static final Object PRESENT = new Object();  在开头就定义这个了
     * 3.      public boolean add(E e) {
     *               return map.put(e, PRESENT)==null;
     *          }                 调用的是HashMap的put方法
     * 4.
     */
    private static void SetNotSafe() {
        //Set<String> set = new HashSet<String>();
        //Set<String> set = Collections.synchronizedSet(new HashSet<String>());
        Set<String> set = new CopyOnWriteArraySet<String >();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                //这个东西是用来生成随机的字符串的
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, i + "").start();
        }
    }

    /**
     * List的不安全
     */
    private static void ListNotSafe() {
        List<String > list= new CopyOnWriteArrayList();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                //这个东西是用来生成随机的字符串的
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },i+"").start();
        }
    }
}
