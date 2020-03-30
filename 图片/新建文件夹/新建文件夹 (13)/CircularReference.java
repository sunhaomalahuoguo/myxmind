package juc;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/29
 * @since 1.0.0
 */
public class CircularReference {

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        a.b = b;
        b.a = a;
    }
}
class A{
    public B b;
}
class B{
    public A a;
}