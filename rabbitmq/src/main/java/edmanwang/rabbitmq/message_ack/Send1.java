package edmanwang.rabbitmq.message_ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import edmanwang.rabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send1 {

    private static final String EXCHANGE_NAME_MESSAGE_ACK = "exchange_name_message_ack";

        public static void main(String[] args) throws IOException, TimeoutException {
            Connection connection = ConnectionUtil.getConnection();

            Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME_MESSAGE_ACK, "fanout");

        try {
            channel.txSelect();
            String message = "edmanwang";
            channel.basicPublish(EXCHANGE_NAME_MESSAGE_ACK, "", null, message.getBytes());
            // 事务回滚
            System.out.println(1/0);
            channel.txCommit();
        } catch (Exception e) {
            System.out.println("消息发布到rabbitmq出现异常");
            channel.txRollback();
        } finally {
            channel.close();
            connection.close();
        }
    }
}
