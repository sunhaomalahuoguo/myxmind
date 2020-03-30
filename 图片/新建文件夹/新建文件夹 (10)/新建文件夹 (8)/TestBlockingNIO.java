package nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *  阻塞式IO演示
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/5
 * @since 1.0.0
 */
public class TestBlockingNIO {

    //客户端
    @Test
    public void client() throws IOException {
        //1.获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

        //2.分配指定大小缓冲区
        ByteBuffer bBuf = ByteBuffer.allocate(1024);

        //3.读取本地文件，并发送到服务器
        while(inChannel.read(bBuf) != -1){
            bBuf.flip();
            sChannel.write(bBuf);
            bBuf.clear();
        }

        //4.关闭通道
        sChannel.close();
        inChannel.close();
    }

    //服务端
    @Test
    public void server() throws IOException {
        //1.获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        FileChannel fChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //2.绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        //3.获取客户端连接的通道
        SocketChannel sChannel = ssChannel.accept();

        //4.分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //5.接收客户端数据，并保存到本地
        while (sChannel.read(buf) != -1){
            buf.flip();
            //6.写入本地
            fChannel.write(buf);
            buf.clear();
        }

        //6.关闭通道
        fChannel.close();
        sChannel.close();
        ssChannel.close();
    }
}