package nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.SortedMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/4
 * @since 1.0.0
 */
public class TestChannel {

    //    819MB的文件
    //    IO耗费时间：25634毫秒25.634秒
    //    NIO耗费时间：10065毫秒10秒

    @Test
    public void test6() throws Exception {
        Charset gbk = Charset.forName("GBK");
        
        //获取编码器
        CharsetEncoder encoder = gbk.newEncoder();
        //获取解码器
        CharsetDecoder decoder = gbk.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("尚硅谷威武");
        cBuf.flip();
        //编码
        ByteBuffer bBuf = encoder.encode(cBuf);

        for (int i = 0; i < 10; i++) {
            System.out.println(bBuf.get());
        }
        bBuf.flip();
        //解码
        CharBuffer dBuf = decoder.decode(bBuf);
        System.out.println(dBuf.toString());
    }
    
    //字符集
    @Test
    public void test5() throws Exception {
        Map<String, Charset> map = Charset.availableCharsets();
        map.entrySet().stream().forEach(System.out::println);
    }

    //分散和聚集
    @Test
    public void test4() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt","rw");
        //1.获取通道
        FileChannel channel = randomAccessFile.getChannel();
        //2.分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);
        //3.分散读取
        ByteBuffer[] bufs = {buf1,buf2};
        channel.read(bufs);

        for (ByteBuffer buf : bufs) {
            buf.flip();
        }
        
        System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
        System.out.println("==========================================");
        System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));

        //4.聚集希尔
        RandomAccessFile randomAccessFile2 = new RandomAccessFile("2.txt","rw");
        //1.获取通道
        FileChannel channel2 = randomAccessFile2.getChannel();

        channel2.write(bufs);

        channel.close();
        channel2.close();

    }

    //通道之间的数据传输（直接缓冲区）
    @Test
    public void test3() throws IOException {
        long start = System.currentTimeMillis();

        FileChannel inChannel = FileChannel.open(Paths.get("develop.7z"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("develop4.7z"), StandardOpenOption.WRITE,StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

//        inChannel.transferTo(0,inChannel.size(),outChannel);
        outChannel.transferFrom(inChannel,0,inChannel.size());

        inChannel.close();
        outChannel.close();

        long end = System.currentTimeMillis();
        System.out.println("NIO耗费时间：" + (end-start) + "毫秒"+ (end-start)/1000 + "秒");
    }

    //2.使用直接缓冲区完成文件的复制(内存映射文件)
    @Test
    public void test2() throws IOException {
        long start = System.currentTimeMillis();

        FileChannel inChannel = FileChannel.open(Paths.get("develop.7z"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("develop3.7z"), StandardOpenOption.WRITE,StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

        //内存映射文件
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer ourMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        //直接缓冲区进行数据的读写操作
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        ourMappedBuf.put(dst);

        inChannel.close();
        outChannel.close();

        long end = System.currentTimeMillis();
        System.out.println("NIO耗费时间：" + (end-start) + "毫秒"+ (end-start)/1000 + "秒");
    }

    //1.利用通道完成文件的复制(非直接缓冲区)
    @Test
    public void test1(){
        long start = System.currentTimeMillis();

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("develop.7z");
            fos = new FileOutputStream("develop1.7z");

            //①获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //②分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            //③将通道中的数据存入缓冲区中
            while (inChannel.read(buf) != -1){
                buf.flip();//切换读取数据的模式
                //④将缓冲区中的数据写入通道中
                outChannel.write(buf);
                buf.clear();//清空缓冲区
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        long end = System.currentTimeMillis();
        System.out.println("NIO耗费时间：" + (end-start));
    }


}