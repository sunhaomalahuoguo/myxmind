package reference;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/31
 * @since 1.0.0
 */
public class StrongReferenceDemo {

    public static void main(String[] args) {
        // 这样定义的默认就是强引用
        Object obj1 = new Object();
        // obj2引用赋值
        Object obj2 = obj1;
        obj1 = null;
        System.gc();
        System.out.println(obj2);
    }

}