package JUC_basic_code;

/**
 * Author:liang;
 * Date:2020/3/15;
 * Time:15:26;
 * Package Name:JUC_basic_code;
 * 需求：
 * 步骤：
 *
 *
 * 实现接口的高效形式
 * 当线程任务比较简单的时候可以直接使用lambda表达式，无需再定义类实现runnable接口
 *
 * 0.   函数的参数是接口或者new一个接口且接口只有一个方法时使用Lambda表达式
 * 1.   Lambda表达式,因为接口就一个方法肯定不会实现错，必须是一个方法。
 *      解决了匿名内部类代码冗余的情况.函数式接口才能用lambda
 * 2.   小括号，箭头，大括号实现
 * 3.   @FunctionalInterface显示声明函数式接口
 * 4.   default 在函数式接口中可以实现多个 default函数
 * 5.   static  可以实现多个static函数
 * 6.   Java8技术
 */


//显示声明函数式接口
@FunctionalInterface
interface Foo{
    int  add(int x,int y);
    default int mul(int x,int y)
    {
        return x*y;
    }
    static int div(int x,int y)
    {
        return x/y;
    }

}
public class _2LambdaExpress {
    public static void main(String[] args) {
        /*冗杂的匿名内部类写法
        Foo foo=new Foo() {
            @Override
            public void sayhello() {
                System.out.println("hello.....");
            }
        };
        foo.sayhello();*/
        Foo foo=(int x,int y)->{
            System.out.println("add method");
            return x+y;
        };
        System.out.println(foo.add(3, 5));
    }
}
