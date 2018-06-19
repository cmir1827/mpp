package com;

import com.google.gson.Gson;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.repositories.UserRepo;
import com.services.UsersService;
import com.util.JdbcUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by sergiubulzan on 20/06/2017.
 */
public class RabMQServer implements IServer {
    public static final String RPC_QUEUE_NAME = "ts_rpc_queue";

    static Channel channel;

    public RabMQServer(){    }

    public void start(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = null;
        try{
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(RPC_QUEUE_NAME, false,false,false,null);
            channel.basicQos(1);
            System.out.println(" [x] Awaiting RPC requests");

            channel.exchangeDeclare("notifications", BuiltinExchangeType.DIRECT);
            String severity = "info";

            RabMQRequestHandler handler = new RabMQRequestHandler(channel,connection);
            new Thread(handler).start();

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
