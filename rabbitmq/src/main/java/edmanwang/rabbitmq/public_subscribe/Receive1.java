package edmanwang.rabbitmq.public_subscribe;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import edmanwang.rabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式
 */
public class Receive1 {

    private static final String EXECHANGE_NAME = "test_exchange_name";

    private static final String QUEUE_EMAIL = "queue_sms";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();

        final Channel channel = connection.createChannel();

        // 首先需要定义一个队列
        channel.queueDeclare(QUEUE_EMAIL, false, false, false, null);

        // 队列绑定交换机
        channel.queueBind(QUEUE_EMAIL, EXECHANGE_NAME, "");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    System.out.println("收到消息---[sms]->" + new String(body, "utf-8"));
                } catch (Exception e) {
                    System.out.println(e.getStackTrace());
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        channel.basicConsume(QUEUE_EMAIL, consumer);
    }
}
