package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publisher {

    private static final String QUEUE_NAME = "LOG";
    private static final String HOST = "localhost";

    public static void main(String args[]) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);

        try(
                Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("The message sended");

        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }

    }

}
