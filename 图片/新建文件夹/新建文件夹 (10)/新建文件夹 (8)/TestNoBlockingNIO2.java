package nio;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 *  非阻塞式NIO演示（UDP传输模式）
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/5
 * @since 1.0.0
 */
public class TestNoBlockingNIO2 {

    //发送端
    @Test
    public void send() throws IOException {
        //1.获取通道
        DatagramChannel sChannel = DatagramChannel.open();

        //2.切换成非阻塞模式
        sChannel.configureBlocking(false);

        //3.分配指定大小缓冲区
        ByteBuffer bBuf = ByteBuffer.allocate(1024);

        //4.发送时间给服务器
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            String str = scanner.next();
            bBuf.put((new Date()+"\t"+str).toString().getBytes());
            bBuf.flip();
            sChannel.send(bBuf,new InetSocketAddress("127.0.0.1", 9898));
            bBuf.clear();
        }

        //4.关闭通道
        sChannel.close();
    }

    //接收端
    @Test
    public void receive() throws IOException {
        //1.获取通道
        DatagramChannel ssChannel = DatagramChannel.open();

        //2.切换成非阻塞模式
        ssChannel.configureBlocking(false);

        //3.绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        //4.获取选择器
        Selector selector = Selector.open();

        //5.将通道注册到选择器上,并且指定"监听读事件"
        ssChannel.register(selector, SelectionKey.OP_READ);

        //6.轮询式的获取选择器上已经"准备就绪"的事件
        while(selector.select()>0){
            //7.获取当前选择器中所有注册的“选择键（可读状态的监听事件）”
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            
            while (it.hasNext()){
                //8.获取准备“可读状态”的事件
                SelectionKey sk = it.next();

                if(sk.isReadable()){

                    //14.读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    ssChannel.receive(buffer);
                    
                    buffer.flip();
                    
                    System.out.println(new String(buffer.array(),0, buffer.limit()));

                    buffer.clear();
                }
                //15.取消选择键SelectionKey
                it.remove();
            }

        }
        ssChannel.close();
    }
}