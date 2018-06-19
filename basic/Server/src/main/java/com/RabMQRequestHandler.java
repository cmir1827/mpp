package com;

import com.net.NetRequest;
import com.net.NetResponse;
import com.net.RequestType;
import com.net.ResponseType;
import com.rabbitmq.client.*;
import com.util.NetSerialization;

import java.io.IOException;

/**
 * Created by sergiubulzan on 20/06/2017.
 */
public class RabMQRequestHandler extends AbstractRequestHandler{

    Channel channel;
    Connection connection;

    public RabMQRequestHandler( Channel channel, Connection connection) {
        super();
        this.channel = channel;
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                            .Builder()
                            .correlationId(properties.getCorrelationId())
                            .build();

                    NetResponse response = null;
                    NetRequest bsRequest = null;
                    try {
                        bsRequest = (NetRequest) NetSerialization.deserialize(body);

                        System.out.println("I have recieved a requst: " + bsRequest.getRequestType());

                        response = getResponseForRequest(bsRequest, properties.getReplyTo());

                    } catch (RuntimeException e) {
                        System.out.println("Runtime Error: [.] " + e.toString());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {

                        byte[] responseData = NetSerialization.serialize(response);

                        System.out.println("Reply to: " + properties.getReplyTo());

                        System.out.println("-------------------");
                        channel.basicPublish("", properties.getReplyTo(), replyProps, responseData);
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };

            channel.basicConsume(RabMQServer.RPC_QUEUE_NAME, false, consumer);

            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException _ignore) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null)
                try {
                    connection.close();
                } catch (IOException _ignore) {}
        }
    }


    @Override
    public void sendCustomNotification(String destination, NetResponse response) {
        try {
            byte[] responseData = NetSerialization.serialize(response);
            channel.basicPublish("notifications", destination + "_notify", null, responseData);

            System.out.println("[x] NOTIFICATION SENT for " + destination + "_notify" + " [x]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendNotification(NetResponse response) {
        for( String desination : userMap.values()){
            System.out.println(desination);
            sendCustomNotification(desination,response);
        }
    }
}
