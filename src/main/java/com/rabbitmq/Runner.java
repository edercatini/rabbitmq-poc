package com.rabbitmq;

import java.util.concurrent.TimeUnit;

import com.rabbitmq.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    private RabbitMQConfig config;
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    @Autowired
    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate, RabbitMQConfig config) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
        this.config = config;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Sending message...");
        rabbitTemplate.convertAndSend(config.topicExchangeName, "foo.bar.baz", "A message!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}