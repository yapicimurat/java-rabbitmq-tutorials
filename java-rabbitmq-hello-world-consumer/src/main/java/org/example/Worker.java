package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Worker {

    public static String QUEUE_NAME = "hello";
    public static String HOST = "localhost";
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            try {
                doWork(message);
            }
            catch(InterruptedException ex) {

            } finally{
                System.out.println(" [x] Done");
            }


            System.out.println(" [x] Received '" + message + "'");
        };

        boolean autoAck = true;

        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
    }

    public static void doWork(String task) throws InterruptedException {
        for(char c : task.toCharArray()) {
            if(c == '.')
                Thread.sleep(1000);
        }
    }
}