package JUC_basic_code;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author:liang;
 * Date:2020/3/15;
 * Time:19:25;
 * Package Name:JUC_basic_code;
 *
 * 步骤：
 *
 * notify只能随机唤醒一个
 * signal可以实现精准唤醒
 * 这样可以极大减少资源的消耗
 *
 * Condition就是钥匙
 *
 * 多线程第四句口诀---唤醒时修改标志位
 *
 *
 *
 * 需求：多线程之间按照A->B->C的顺序执行
 * 要求：A打印1次，B打印2次，C打印3次
 * 3轮
 */

class shareSource
{
    /*
     * 标志位，锁和钥匙
     */
    private int sign=0;//A是0，B是1，C是2   标志位
    private Lock lock=new ReentrantLock();
    private Condition condition1=lock.newCondition();
    private Condition condition2=lock.newCondition();
    private Condition condition3=lock.newCondition();
    ArrayList<Condition> arrayList=new ArrayList<Condition>();
    public shareSource()
    {
        arrayList.add(condition1);
        arrayList.add(condition2);
        arrayList.add(condition3);
    }
    /*
     * 高内聚函数
     *
     * 首先trylock
     * 在try里面如果有线程通信，就进行 判断、干活、唤醒
     */
    public void print(int id)
    {
        lock.lock();
        try{
            //1.判断
            while(id!=this.sign)
            {
                arrayList.get(id).await();
            }
            //2.干活
            switch (id)
            {
                case 0:System.out.println(Thread.currentThread().getName());break;
                case 1:
                    for (int i = 0; i < 2; i++) {
                        System.out.println(Thread.currentThread().getName());
                    };break;
                case 2:
                    for (int i = 0; i < 3; i++) {
                        System.out.println(Thread.currentThread().getName());
                    }
            }
            //3.唤醒并修改标志位
            sign=(sign+1)%3;
            arrayList.get(sign).signal();

        }catch(Exception e)
        {
              e.printStackTrace();
        }
         finally {
              lock.unlock();
         }
    }

}
public class _5ThreeOrder {
    public static void main(String[] args) {

        shareSource shareSource=new shareSource();

        new Thread(()->{
            for (int i = 0; i <3 ; i++) {
                shareSource.print(0);
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i <3 ; i++) {
                shareSource.print(1);
            }
        },"B").start();        new Thread(()->{
            for (int i = 0; i <3 ; i++) {
                shareSource.print(2);
            }
        },"C").start();

    }
}
