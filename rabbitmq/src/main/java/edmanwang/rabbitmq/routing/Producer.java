package edmanwang.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import edmanwang.rabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式
 */
public class Producer {

    private static final String EXECHANGE_NAME = "test_exechange_routing_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXECHANGE_NAME, "direct");

        String message = "edmanwang routing message";

        channel.basicPublish(EXECHANGE_NAME, "info", null, message.getBytes());

        channel.close();

        connection.close();
    }
}
