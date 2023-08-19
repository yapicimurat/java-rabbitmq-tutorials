package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Consumer {

    private static final String QUEUE_NAME = "LOG";
    public static String HOST = "localhost";
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Waiting for messages to consume :)");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String receivedMessage = new String(delivery.getBody(), StandardCharsets.UTF_8).intern();
            System.out.println("The message received: " + receivedMessage);
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> System.out.println("System shutdown..."));

    }


}