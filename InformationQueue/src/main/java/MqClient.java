import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 访问消息队列的客户端
 * 此类提供与本地端口9999的Socket链接
 * 仅仅具备生产消息和消费消息的方法
 */

public class MqClient {
    //生产消息
    public static void produce(String msg) throws IOException {
        //本地的BrokerServer.PORT创建SOCKET
        Socket socket = new Socket(InetAddress.getLocalHost(),BrokerServer.PORT);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(msg);
        out.flush();
    }

    //消费消息
    public static String consume() throws IOException{
        Socket socket = new Socket(InetAddress.getLocalHost(),BrokerServer.PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream());

        //先向消息队列发送命令
        out.println("CONSUMES");
        out.flush();

        //从消息队列获取一条信息
        String str = in.readLine();

        return str;
    }
}
