package edmanwang.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import edmanwang.rabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * topic 模式
 */
public class TopicProducer {

    private static final String TEST_EXCHANGE_TOPIC_NAME = "test_exchange_topic_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(TEST_EXCHANGE_TOPIC_NAME, "topic");

        String message = "edmanwang";

        channel.basicPublish(TEST_EXCHANGE_TOPIC_NAME, "goods.delete", null, message.getBytes());

        channel.close();

        connection.close();
    }
}
