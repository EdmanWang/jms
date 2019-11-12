package edmanwang.rabbitmq.message_ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import edmanwang.rabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send2 {

    private static final String EXCHANGE_NAME_MESSAGE_ACK = "exchange_name_message_ack";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME_MESSAGE_ACK, "fanout");

        // 首先是设置channel为confirm模式
        channel.confirmSelect();

        String message = "edmanwang";
        channel.basicPublish(EXCHANGE_NAME_MESSAGE_ACK, "", null, message.getBytes());

        if (channel.waitForConfirms()) {
            // 如果接收到了确认消息的话，就是表示消息发送成功了
            System.out.println("消息成功发送到了rabbitmq服务器了");
        } else {
            System.out.println("消息未发送到rabbitma服务器");
        }
        channel.close();
        connection.close();

    }
}
