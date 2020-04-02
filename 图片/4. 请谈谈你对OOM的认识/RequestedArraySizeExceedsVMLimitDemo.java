package error;


/* 请注意, 在后两次迭代抛出 java.lang.OutOfMemoryError: Requested array size exceeds VM limit 错误之前,
 先抛出了2次 java.lang.OutOfMemoryError: Java heap space 错误。 这是因为 2^31-1 个 int 数占用的内存超过了JVM默认的8GB堆内存。
 此示例也展示了这个错误比较罕见的原因 —— 要取得JVM对数组大小的限制, 要分配长度差不多等于 Integer.MAX_INT 的数组.
 这个示例运行在64位的Mac OS X, Hotspot 7平台时, 只有两个长度会抛出这个错误: Integer.MAX_INT-1 和 Integer.MAX_INT。*/

public class RequestedArraySizeExceedsVMLimitDemo {

    public static void main(String[] args) {
        for (int i = 3; i >= 0; i--) {
            try {
                int[] arr = new int[Integer.MAX_VALUE-i];
                System.out.format("Successfully initialized an array with %,d elements.\n", Integer.MAX_VALUE-i);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}