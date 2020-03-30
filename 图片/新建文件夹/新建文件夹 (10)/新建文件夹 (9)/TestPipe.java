package nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/5
 * @since 1.0.0
 */
public class TestPipe {

    @Test
    public void test1() throws IOException {
        //1.获取管道
        Pipe pipe = Pipe.open();

        //2.将缓冲区中的数据写入到管道
        Pipe.SinkChannel sinkChannel = pipe.sink();

        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put("通过单向管道发送数据".getBytes());
        buf.flip();
        sinkChannel.write(buf);

        //3.读取缓冲区中的数据
        Pipe.SourceChannel source = pipe.source();
        buf.flip();
        int len = source.read(buf);
        System.out.println(new String(buf.array(),0,len));

        source.close();
        sinkChannel.close();

    }
}