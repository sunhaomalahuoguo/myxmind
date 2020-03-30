package juc;

public class HelloGC {

    public static void main(String[] args) throws Exception {
        // 返回java虚拟机中的内存总量
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("totalMemory（-Xms） = " + totalMemory + " （字节）、" + (totalMemory / (double) 1024 / 1024) + " MB");
        // 返回java虚拟机试图使用的最大内存量
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("maxMemory（-Xmx） = " + maxMemory + " （字节）、" + (maxMemory / (double) 1024 / 1024) + " MB");
    }

}