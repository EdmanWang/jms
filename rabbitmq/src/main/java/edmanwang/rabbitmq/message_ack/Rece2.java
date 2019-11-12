package edmanwang.rabbitmq.message_ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import edmanwang.rabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Rece2 {

    private static final String EXCHANGE_NAME_MESSAGE_ACK = "exchange_name_message_ack";

    private static final String MESSAGE_ACK_QUEUE_NAME = "message_ack_queue_name_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        final Channel channel = connection.createChannel();

        channel.queueDeclare(MESSAGE_ACK_QUEUE_NAME, false, false, false, null);

        channel.queueBind(MESSAGE_ACK_QUEUE_NAME, EXCHANGE_NAME_MESSAGE_ACK, "", null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    System.out.println("【2】 收到消息 ---> " + new String(body));
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(MESSAGE_ACK_QUEUE_NAME, consumer);
    }
}
