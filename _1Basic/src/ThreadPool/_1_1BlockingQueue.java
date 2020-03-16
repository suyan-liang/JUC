package ThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Author:liang;
 * Date:2020/3/16;
 * Time:13:48;
 * Package Name:ThreadPool;
 *
 *
 * 需求：阻塞队列
 * 比喻成火锅店的候客区
 *
 * 当队列是空的，从队列中获取元素的操作会被阻塞
 * 当队列是满的，从队列中添加元素的操作会被阻塞
 *
 * 这个过程是自动的，也就是我们不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程，
 * 这些操作都被BlockingQueue一手包办了
 *
 *
 *
 * 	        Throws exception	Special value	Blocks	            Times out
 *
 * Insert	add(e)	            offer(e)	    put(e)	            offer(e, time, unit)
 * Remove	remove()	        poll()	        take()	            poll(time, unit)
 * Examine	element()	        peek()	        not applicable	    not applicable
 *
 *
 *
 * 重点在于_1_2的那张图
 *
 */
public class _1_1BlockingQueue {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue=new ArrayBlockingQueue<>(3);//容量3
    }
}
