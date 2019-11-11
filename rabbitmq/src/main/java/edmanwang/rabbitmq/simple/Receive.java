package edmanwang.rabbitmq.simple;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import edmanwang.rabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive {
    private static final String QUEUE_NAME = "test_simple_queue_name";

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("receive msg ->" + msg);
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumer);

    }

    public void oldApi(Channel channel) throws IOException, InterruptedException {
        QueueingConsumer consumer = new QueueingConsumer(channel);

        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            byte[] body = delivery.getBody();

            String msg = new String(body);

            System.out.println("receive msg ->" + msg);
        }
    }
}
