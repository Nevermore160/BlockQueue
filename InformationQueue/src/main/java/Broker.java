import java.util.concurrent.ArrayBlockingQueue;

/**
 * 消息处理中心
 * 维护一个队列提供生产消息和消费消息的方法
 * 仅仅具备存储服务功能
 */

public class Broker {
    //队列中存储消息的最大数量
    private final static int MAX_SIZE = 5;
    //保存消息数据的容器
    private static ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(MAX_SIZE);

    //生产消息
    public static void produce(String msg){
        if(messageQueue.offer(msg))
            System.out.println("成功传递消息" + msg);
        else
            System.out.println("消息中心队列已经存满，不能继续放入消息了！！！");
    }

    //消费消息
    public static String consume(){
        String msg = messageQueue.poll();
        if(msg != null)
            //消费条件满足
            System.out.println("取出消息:" + msg);
        else
            System.out.println("消息队列中没有消息，请先存入");

        return msg;
    }
}
