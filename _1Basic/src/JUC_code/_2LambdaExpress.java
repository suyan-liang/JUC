package JUC_code;

/**
 * Author:liang;
 * Date:2020/3/15;
 * Time:15:26;
 * Package Name:JUC_code;
 * 需求：
 * 步骤：
 *
 *
 * 实现接口的高效形式
 * 1.   Lambda表达式,当接口中只有一个方法时简便的写法，因为就一个方法肯定不会实现错，必须是一个方法。解决了匿名内部类代码冗余的情况.函数式接口才能用lambda
 * 2.   小括号，箭头，大括号实现
 * 3.   @FunctionalInterface显示声明函数式接口
 * 4.   default 在函数式接口中可以实现多个 default函数
 * 5.   static  可以实现多个static函数
 * 6.   Java8技术
 */


//显示声明函数式接口
@FunctionalInterface
interface Foo{
    public abstract int  add(int x,int y);
    public default int mul(int x,int y)
    {
        return x*y;
    }
    public static int div(int x,int y)
    {
        return x/y;
    }

}
public class _2LambdaExpress {
    public static void main(String[] args) {
        /*        Foo foo=new Foo() {
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
