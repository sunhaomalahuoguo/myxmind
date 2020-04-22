package sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 对数组进行排序
 * @author sunhao
 * @create 2020/4/22
 * @since 1.0.0
 */
public class ArraySort {

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{2,324,4,57,1};
        //升序排序
        Arrays.sort(arr);
        System.out.println("增序排序结果："+ Arrays.toString(arr));

        //降序排序
        Arrays.sort(arr,new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });

        System.out.println("降序排序结果："+ Arrays.toString(arr));
    }
}