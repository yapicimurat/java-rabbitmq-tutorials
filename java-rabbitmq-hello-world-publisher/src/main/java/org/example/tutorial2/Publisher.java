package org.example.tutorial2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Publisher {

    private static final String QUEUE_NAME = "TUTORIAL2";
    private static final String HOST_NAME = "localhost";
    public static void main(String args[]) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST_NAME);
        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()){
            String message = String.join(" ", args);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("Message sended");

        }catch (Exception exception) {
            System.out.println(exception.getMessage());
        }


    }
}
