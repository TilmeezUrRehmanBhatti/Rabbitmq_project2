package com.project2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    private static final String EXCHANGE_TOPIC = "topic_logs";
    private static final String EXCHANGE_FANOUT = "logs";
    /**
     * Publish Messages with different routing keys.
     *
     * @throws IOException
     * @throws TimeoutException
     */
    public static void publishMessage(String message, String routingKey) throws IOException, TimeoutException {
        Channel channel = ConnectionManager.getConnection().createChannel();
//        channel.exchangeDeclare(EXCHANGE_TOPIC,"topic");
        channel.basicPublish(EXCHANGE_TOPIC, routingKey, null, message.getBytes());
        System.out.println(" [x] Sent '" + "health.education" + "':'" + message + "'");
//        channel.close();
    }
//    public Producer() throws IOException, TimeoutException {
//        //Creating a connection to the server
//        ConnectionFactory factory = new ConnectionFactory();
//        // Inserting the data of RabbitMQ administration account
//        factory.setUsername("studentx");
//        factory.setPassword("studentx");
//
//        // Inserting the IP of the machine where the server is running
//        factory.setHost("127.0.0.1");
//        factory.setPort(5672);
//        try (
//                Connection connection = factory.newConnection();
//                Channel channel = connection.createChannel()) {
//            channel.exchangeDeclare(EXCHANGE_TOPIC,"topic");
//
//            String message = "Drink a lot of Water and stay Healthy!";
//            System.out.println("At producer");
//            String routingKey ="health.education";
//            channel.basicPublish(EXCHANGE_TOPIC, routingKey, null, message.getBytes("UTF-8"));
//            System.out.println(" [x] Sent '" + "health.education" + "':'" + message + "'");
//
//        }
//    }
}
