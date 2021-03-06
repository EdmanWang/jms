package edmanwang.rabbitmq.topic;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import edmanwang.rabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicConsumer1 {

    private static final String TEST_EXCHANGE_TOPIC_NAME = "test_exchange_topic_name";

    private static final String TOPIC_GOODS_UPDATE_QUEUE_NAME = "topic_goods_update_queue_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        final Channel channel = connection.createChannel();

        channel.queueDeclare(TOPIC_GOODS_UPDATE_QUEUE_NAME, false, false, false, null);

        channel.queueBind(TOPIC_GOODS_UPDATE_QUEUE_NAME, TEST_EXCHANGE_TOPIC_NAME, "goods.update");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    String message = new String(body, "utf-8");
                    System.out.println("consumer [1] receive message ----> " + message);
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        channel.basicConsume(TOPIC_GOODS_UPDATE_QUEUE_NAME, consumer);
    }
}
