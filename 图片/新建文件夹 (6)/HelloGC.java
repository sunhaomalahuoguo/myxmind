package juc;

public class HelloGC {

    public static void main(String[] args) throws Exception {
        // -Xms10m -Xmx10m
        System.out.println("********** HelloGC **********");
        byte[] byteArray = new byte[50 * 1024 * 1024];
    }

}