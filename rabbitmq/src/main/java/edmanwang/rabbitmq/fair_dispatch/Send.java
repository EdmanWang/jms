package edmanwang.rabbitmq.fair_dispatch;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import edmanwang.rabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME = "fair_dispatch_queue";

    private static final int prefetchCount = 1;

    private static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicQos(prefetchCount);

        String msg = "wgx";

        for (int i = 0; i < 50; i++) {

            String value = msg + i;

            System.out.println("send values" + value);

            channel.basicPublish("", QUEUE_NAME, null, value.getBytes());

            Thread.sleep(random.nextInt(500));
        }

        channel.close();

        connection.close();
    }
}
