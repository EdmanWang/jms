package edmanwang.rabbitmq.public_subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import edmanwang.rabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String EXECHANGE_NAME = "test_exchange_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        // 创建交换机
        channel.exchangeDeclare(EXECHANGE_NAME, "fanout");

        String msg = "edmanwang";

        channel.basicPublish(EXECHANGE_NAME, "", null, msg.getBytes());

        System.out.println("send value" + msg);

        channel.close();

        connection.close();
    }
}
