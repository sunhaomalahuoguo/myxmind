package sorts;

import java.util.Arrays;

/**
 * 〈希尔排序〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/6/12
 * @since 1.0.0
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = new int[]{1,5,8,2,3,9,4};
        System.out.println("排序前："+Arrays.toString(arr));
        arr = shellSort(arr);
        System.out.println("排序后："+Arrays.toString(arr));
    }

    private static int[] shellSort(int[] arr) {
        int len = arr.length;
        for (int gap = (int)Math.floor(len / 2); gap > 0; gap = (int)Math.floor(gap / 2)) {
            for (int i = gap; i < len; i++) {
                int j = i;
                int current = arr[i];
                while (j - gap >= 0 && current < arr[j - gap]) {
                    arr[j] = arr[j - gap];
                    j = j - gap;
                }
                arr[j] = current;
            }
        }
        return arr;
    }
}