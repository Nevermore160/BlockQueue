import java.io.IOException;

public class ConsumeClient {
    public static void main(String[] args) throws IOException {
        MqClient client = new MqClient();
        String message = client.consume();

        System.out.println("获取到底消息为：" + message);
    }
}
