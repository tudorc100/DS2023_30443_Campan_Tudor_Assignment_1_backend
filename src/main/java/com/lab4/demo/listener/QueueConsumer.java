package com.lab4.demo.listener;

import com.lab4.demo.consumption.Consumption;
import com.lab4.demo.device.DeviceService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class QueueConsumer {

    @Autowired
    private DeviceService deviceService;

    public void readMessages() throws Exception, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("cow.rmq2.cloudamqp.com");
        factory.setUsername("jvaugkfy");
        factory.setPassword("8capBwaJfuNklKDQxd43P2xsWfPVFiG0");
        factory.setPort(5672);
        factory.setUri("amqps://jvaugkfy:8capBwaJfuNklKDQxd43P2xsWfPVFiG0@cow.rmq2.cloudamqp.com/jvaugkfy");


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        channel.queueDeclare("DS", true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            System.out.println(" [x] Received '" + message + "'");

            try {
                Thread.sleep(4000);
                addEnergy(message);
                Thread.sleep(4000);
            } catch (InterruptedException | JSONException e) {
                throw new RuntimeException(e);
            }
        };
        channel.basicConsume("DS", true, deliverCallback, consumerTag -> {
        });
    }

    private void addEnergy(String message) throws InterruptedException, JSONException {
        JSONObject jsonObject = new JSONObject(message);
        System.out.println(jsonObject.get("timestamp"));
        System.out.println(jsonObject.get("deviceid"));
        System.out.println(jsonObject.get("measurmentvalue"));
        Timestamp timestamp = Timestamp.valueOf((String) jsonObject.get("timestamp"));
        Long device_id = (Long) jsonObject.get("deviceid");
        Double energy = Double.parseDouble((String) jsonObject.get("measurmentvalue"));



        Consumption consumption = new Consumption(timestamp, energy, device_id);
        deviceService.addConsumption(consumption);

    }
}