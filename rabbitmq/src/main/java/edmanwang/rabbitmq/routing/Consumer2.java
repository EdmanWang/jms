package edmanwang.rabbitmq.routing;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import edmanwang.rabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer2 {

    private static final String EXECHANGE_NAME = "test_exechange_routing_name";

    private static final String ROUNTING_QUEUE_TWO_NAME = "rounting_queue_two_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        final Channel channel = connection.createChannel();

        channel.queueDeclare(ROUNTING_QUEUE_TWO_NAME, false, false, false, null);

        channel.queueBind(ROUNTING_QUEUE_TWO_NAME, EXECHANGE_NAME, "info");
        channel.queueBind(ROUNTING_QUEUE_TWO_NAME, EXECHANGE_NAME, "error");
        channel.queueBind(ROUNTING_QUEUE_TWO_NAME, EXECHANGE_NAME, "warn");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    System.out.println("consumer 【2】 reveice value ---->" + new String(body));
                }finally {
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        channel.basicConsume(ROUNTING_QUEUE_TWO_NAME, consumer);

    }
}
