import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.*;

/**
 * 用于启动消息处理中心
 * 将 Broker发布为服务到本地9999端口
 * 监听本地9999端口的Socket链接
 * 在接受的信息中进行我们的协议校验
 * 这里仅仅具备接受消息,校验协议,转发消息功能
 * 使用SEND和CONSUMES标识生产和消费
 */

public class BrokerServer implements Runnable {

    public static int PORT = 9999;
    private final Socket socket;

    public BrokerServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            String str;
            while ((str = in.readLine()) != null) {
                System.out.println("原始数据为:" + str);
            }

            if (str.equals("CONSUMES")) //表示消费一条消息
            {
                //从消息队列中消费一条消息
                String message = Broker.consume();
                out.println(message);
                out.flush();
            } else if (str.equals("SEND")) {
                //表示要生产消息放到队列中
                Broker.produce(str);
            } else
                System.out.println("没有遵守协议，不提供服务");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true){
            BrokerServer brokerServer = new BrokerServer(serverSocket.accept());
            new Thread(brokerServer).start();
        }
    }
}
