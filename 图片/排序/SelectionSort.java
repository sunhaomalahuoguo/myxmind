package sorts;

import java.util.Arrays;

/**
 * 〈选择排序〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/6/12
 * @since 1.0.0
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = new int[]{1,5,8,2,3,9,4};
        System.out.println("排序前："+Arrays.toString(arr));
        arr = selectionSort(arr);
        System.out.println("排序后："+Arrays.toString(arr));
    }

    private static int[] selectionSort(int[] arr) {
        int len = arr.length;
        int minIndex;
        int temp;
        for (int i = 0; i < len -1; i++) {
            minIndex = i;
            for (int j = i + 1; j < len; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;         //选择最小值的索引
                }
            }
            temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        return arr;
    }
}