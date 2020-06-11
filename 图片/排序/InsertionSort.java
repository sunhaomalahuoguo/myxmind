package sorts;

import java.util.Arrays;

/**
 * 〈插入排序〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/6/12
 * @since 1.0.0
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] arr = new int[]{1,5,8,2,3,9,4};
        System.out.println("排序前："+Arrays.toString(arr));
        arr = insertionSort(arr);
        System.out.println("排序后："+Arrays.toString(arr));
    }

    private static int[] insertionSort(int[] arr) {
        int len = arr.length;
        int preIndex;
        int current;
        for (int i = 1; i < len; i++) {
            preIndex = i - 1;
            current = arr[i];
            while (preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;  //将当前元素插入到preIndex元素后
        }
        return arr;
    }
}