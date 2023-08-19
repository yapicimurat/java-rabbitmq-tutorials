package org.example.tutorial2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.concurrent.TimeoutException;

public class Consumer {

    private static final String QUEUE_NAME = "TUTORIAL2";
    private static final String HOST_NAME = "localhost";
    public static void main(String args[]) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST_NAME);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8).intern();
            System.out.println("Messaged received: " + message);
            try {
                doWork(message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {
            System.out.println("System shutdown....");
        });


    }

    private static void doWork(String task) throws InterruptedException {
        for(char ch: task.toCharArray()) {
            if(ch == '.') {
                System.out.println("Task started");
                Thread.sleep(1000);
                System.out.println("Task ended");
            }
        }
    }
}
