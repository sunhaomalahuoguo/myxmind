package sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 自定义对象排序
 * @author sunhao
 * @create 2020/4/22
 * @since 1.0.0
 */
public class ObjectSort {

    public static void main(String[] args) {
        List<User> list = new ArrayList<User>();
        list.add(new User(8,"张飞",45));
        list.add(new User(3,"赵云",67));
        list.add(new User(5,"关羽",32));
        list.add(new User(2,"刘备",23));
        list.add(new User(4,"曹操",78));

        //按照ID升序排列
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getID()-o2.getID();
            }
        });

        System.out.println("按照ID升序排列:"+list.toString());

        //按照年龄降序排列
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.getAge()-o1.getAge();
            }
        });

        System.out.println("按照年龄降序排列:"+list.toString());
    }
}
class User{
    private Integer ID;
    private String name;
    private Integer age;

    public User(Integer ID, String name, Integer age) {
        this.ID = ID;
        this.name = name;
        this.age = age;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}