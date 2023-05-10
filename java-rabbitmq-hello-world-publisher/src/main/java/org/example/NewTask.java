package org.example;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class NewTask {
    private static final String QUEUE_NAME = "hello";
    private static final String HOST = "localhost";

    public static void main(String args[]) throws Exception {
        //Start connection...
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost(HOST);

        //try-with-resources usage
        try (Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = String.join(" ", args);

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            System.out.println("Sent '" + message + "'");


        }

    }

}
