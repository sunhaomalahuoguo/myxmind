package sorts;

import java.util.Arrays;

/**
 * 〈冒泡排序〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/6/12
 * @since 1.0.0
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = new int[]{1,5,8,2,3,9,4};
        System.out.println("排序前："+Arrays.toString(arr));
        arr = bubbleSort(arr);
        System.out.println("排序后："+Arrays.toString(arr));
    }

    private static int[] bubbleSort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len -1; i++) {
            for (int j = 0; j < len -1 -i; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }
}