package _1BasicDemo;

import java.util.concurrent.TimeUnit;

/**
 * Author:liang;
 * Date:2020/3/15;
 * Time:20:34;
 * Package Name:_1BasicDemo;
 * 需求：测试线程八锁
 *
 *
 * 关键：1.    一个类中的同步函数的锁是this,静态同步函数的锁是这个类的字节码文件
 *       2.    某一时刻 ，一把锁只能被一个线程持有
 *
 *
 *
 * 线程八锁
 * 1.   标准访问时,email  or  SMS?-----email
 * 2.   email延时4s,....?--------------email  (下面都延时4s)
 * 3.   新增一个普通方法hello,先打印邮件还是先打印hello?-------------hello
 * 4.   两部手机,一部email,一部SMS,....?------SMS
 * 5.   两个静态同步方法，一部手机，功能还是类似，email or SMS?  email
 * 6.   两个静态同步方法，两部手机，一部email，一部SMS，email or SMS?  -----Email
 * 7.   1个普通同步方法，1个静态同步方法，1部手机，email or SMS?-------SMS
 * 8.   1个普通同步方法，1个静态同步方法，2部手机，email or SMS?-------SMS
 *
 */

class Phone {
    public synchronized void sendEmail() {
        //java新的睡觉工具类，规律----TimeUnit.单位.sleep(值)  需要用try catch环绕
        //单位：DAYS，HOURS，MINUTES，SECONDS，MILLISECONDS，MICROSECONDS等等....
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("email....");
    }

    public synchronized void sendSMS() {
        System.out.println("SMS");
    }

    public void hello() {
        System.out.println("hello");
    }

    public static synchronized void Email()
    {
        try {TimeUnit.SECONDS.sleep(4); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("Email");
    }
    public static synchronized void SMS()
    {
        System.out.println("SMS");
    }
}
public class _6lock8 {
    public static void main(String[] args) {
        Phone phone=new Phone();
        Phone phone2=new Phone();
        new Thread(()->{phone.sendEmail();},"A").start();
        //睡300ms保证email先启动
        try {TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
        new Thread(()->{Phone.SMS();},"B").start();
        //new Thread(()->{phone.hello();},"C").start();

    }
}
