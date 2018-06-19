package com.net;

import com.AbstractClientService;
import com.google.gson.Gson;
import com.rabbitmq.client.*;
import com.util.NetSerialization;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * Created by sergiubulzan on 20/06/2017.
 */
public class RabMQClient extends AbstractClientService {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "ts_rpc_queue";
    private String replyQueueName;


    String corrId;
    private AMQP.BasicProperties props;

    public RabMQClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();
        channel = connection.createChannel();

        //the queue for recieving responses
        replyQueueName = channel.queueDeclare().getQueue();

        //identifier for current client
        corrId = UUID.randomUUID().toString();

        props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        gson = new Gson();
    }

    final BlockingQueue<NetResponse> responses = new ArrayBlockingQueue<NetResponse>(2);


    @Override
    public NetResponse handleRequest(NetRequest request) {
        try {

            System.out.println("I send a request: " + request.getRequestType());
            byte[] requestData = NetSerialization.serialize(request);

            channel.basicPublish("", requestQueueName, props, requestData);
            replyTo = props.getReplyTo();

            channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    if (properties.getCorrelationId().equals(corrId)) {
                        try {
                            NetResponse resp = (NetResponse) NetSerialization.deserialize(body);
                            System.out.println("I have recieved: " + resp.getType() + " with message: " + resp.getMessage());
                            System.out.println("------");
                            responses.offer(resp);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            //wait for a response
            NetResponse resp = responses.take();
            return resp;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    String replyTo;

//
//    @Override
//    public void run() {
//        try {
//            while (replyTo == null){
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            System.out.println("[x] NOTIFICATION SERVICE OPEN [x]");
//            channel.exchangeDeclare("notifications", BuiltinExchangeType.DIRECT);
//            String queueName = channel.queueDeclare().getQueue();
//
//            replyTo += "_notify";
//            channel.queueBind(queueName, "notifications", replyTo);
//
//            Consumer consumer = new DefaultConsumer(channel) {
//                @Override
//                public void handleDelivery(String consumerTag, Envelope envelope,
//                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
//                    try {
//                        NetResponse resp = (NetResponse) NetSerialization.deserialize(body);
//                        System.out.println(" [x] Received notification '" + envelope.getRoutingKey() + "':'" + resp + "'");
//                        notificationRecieved(resp);
//
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//
//            channel.basicConsume(queueName, true, consumer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void run() {
        try {
            while (replyTo == null){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("[x] NOTIFICATION SERVICE OPEN ON " + replyTo + "_notify" + " [x]");



            channel.exchangeDeclare("notifications", BuiltinExchangeType.DIRECT);
            String queueName = channel.queueDeclare().getQueue();

            channel.queueBind(queueName, "notifications", replyTo + "_notify");

            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        NetResponse resp = (NetResponse) NetSerialization.deserialize(body);
                        System.out.println(" [x] Received notification '" + envelope.getRoutingKey() + "':'" + resp + "'");
                        notificationRecieved(resp);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            };

            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
