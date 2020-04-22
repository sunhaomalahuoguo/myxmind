package sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 对集合进行排序
 * @author sunhao
 * @create 2020/4/22
 * @since 1.0.0
 */
public class CollectionSort {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(324);
        list.add(4);
        list.add(57);
        list.add(1);
        //升序排序
        Collections.sort(list);
        System.out.println("升序排序:"+list.toString());
        //降序排序
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });

        System.out.println("降序排序:"+list.toString());
    }
}