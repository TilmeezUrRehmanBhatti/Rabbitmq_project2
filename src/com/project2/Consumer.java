package com.project2;

import com.google.gson.Gson;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class Consumer {
    private static final String EXCHANGE_NAME = "topic_logs";
    /**
     * Assign Consumers to each of the Queue.
     *
     * @throws IOException
     * @throws TimeoutException
     */
    public static void subscribeMessage(String bindingKey ) throws IOException, TimeoutException {
//        TopicExchange.declareExchange();
        String queueName = TopicExchange.declareQueues(bindingKey);
        Channel channel = ConnectionManager.getConnection().createChannel();
        channel.basicConsume(queueName, true, ((consumerTag, delivery) -> {
            System.out.println("\n\n=========== "+ queueName +" Queue ==========");
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        }), consumerTag -> {
            System.out.println(consumerTag);
        });
    }
    private static final String EXCHANGE_TOPIC = "topic_logs";
    private static final String EXCHANGE_FANOUT = "logs";
    public Consumer() throws IOException, TimeoutException {
        try {
            //Creating connection the server
            ConnectionFactory factory = new ConnectionFactory();

            //Inserting data of our RabbitMQ administration account
            factory.setUsername("studentx");
            factory.setPassword("studentx");

            //Inserting the IP of a server where machine is running
            factory.setHost("127.0.0.1");
            factory.setPort(5672);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_FANOUT, "fanout");
            String queueName = channel.queueDeclare().getQueue();

            channel.queueBind(queueName, EXCHANGE_FANOUT, "");

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.println("At consumer");

                String message = new String(delivery.getBody());
                System.out.println(" [x] Received '" +
                        delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
                DataHolder[] msg = new Gson().fromJson(message,DataHolder[].class);
                for (DataHolder dataHolder : msg) {
                    System.out.println(dataHolder);
                }
                List<DataHolder> list = Arrays.asList(msg);
                System.out.println(" [x] Received '" + message + "'");
                for (DataHolder dataHolder : list) {
                    System.out.println(dataHolder);
                }
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
