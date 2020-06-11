package sorts;

import java.util.*;

/**
 * 〈冒泡排序〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/6/12
 * @since 1.0.0
 */
public class BucketSort {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 5, 8, 2, 3, 9, 4};
        System.out.println("排序前：" + Arrays.toString(arr));
        bucketSort(arr);
    }

    public static void bucketSort(int[] arr) {

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }

        //桶数
        int bucketNum = (max - min) / arr.length + 1;
        ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            bucketArr.add(new ArrayList<Integer>());
        }

        //将每个元素放入桶
        for (int i = 0; i < arr.length; i++) {
            int num = (arr[i] - min) / (arr.length);
            bucketArr.get(num).add(arr[i]);
        }

        //对每个桶进行排序
        for (int i = 0; i < bucketArr.size(); i++) {
            Collections.sort(bucketArr.get(i));
        }
        System.out.println("排序后："+bucketArr.toString());
    }
}