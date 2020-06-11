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
public class CountingSort {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 5, 8, 2, 3, 9, 4};
        System.out.println("排序前：" + Arrays.toString(arr));
        arr = countingSort(arr);
        System.out.println("排序后：" + Arrays.toString(arr));
    }


    public static int[] countingSort(int[] arr) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        //找出数组中的最大最小值
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }

        int[] help = new int[max - min + 1];

        //找出每个数字出现的次数
        for (int i = 0; i < arr.length; i++) {
            int mapPos = arr[i] - min;
            help[mapPos]++;
        }

        //计算每个数字应该在排序后数组中应该处于的位置
        for (int i = 1; i < help.length; i++) {
            help[i] = help[i - 1] + help[i];
        }

        //根据help数组进行排序
        int res[] = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int post = --help[arr[i] - min];
            res[post] = arr[i];
        }

        return res;
    }
}