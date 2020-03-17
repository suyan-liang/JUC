package Before;

/**
 * Author:liang;
 * Date:2020/3/17;
 * Time:14:12;
 * Package Name:Before;
 *
 *
 *
 * volatile关键字
 * 0.   被volatile修饰的变量能够保证每个线程能够获取该变量的最新值，从而避免出现数据脏读的现象。
 * 1.   当多个线程操作一个共享数据时，可以保证内存中数据的可见性
 * 2.   通过加锁的方法也可以解决内存可见性的问题，但是效率太低，不值
 * 3.   使用volatile关键字的效率要高于synchronized
 * 4.   volatile解决的问题仅仅是内存可见性问题，无法替代synchronized
 * 5.   volatile无法保证互斥性，即无法实现锁的功能
 * 6.   volatile无法保证数据的原子性
 *
 * 内存可见性
 * 每个线程都会分配一个独立的缓存区，当用到某些数据时，会从主存中拷贝到缓冲区，
 * 修改的话，修改过后会再重新写入主存，
 * 读取的话，可能会来不及从主存中读到修改后的值
 * 使用volatile关键字的话可以看做直接对主存的数据进行操作
 *
 */
