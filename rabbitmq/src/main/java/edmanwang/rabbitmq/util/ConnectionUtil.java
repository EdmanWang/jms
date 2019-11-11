package edmanwang.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 连接到rebbitmq的连接工具类
 */
public class ConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();

        // 设置连接的rabbitmq主机地址
        factory.setHost("127.0.0.1");

        // 设置端口
        factory.setPort(5672);

        // 设置连接的virtual_host 相当于是一个需要连接的数据库
        factory.setVirtualHost("/edmanwang_mmr");

        // 设置用户名
        factory.setUsername("edmanwang");

        // 设置密码
        factory.setPassword("edmanwang");

        return factory.newConnection();
    }
}
