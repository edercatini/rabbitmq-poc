package com.rabbitmq;

import java.util.concurrent.TimeUnit;

import com.rabbitmq.config.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	@MockBean
	private Runner runner;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private Receiver receiver;

	@Autowired
	private RabbitMQConfig config;

	@Test
	public void sendMessageToQueue() throws Exception {
		rabbitTemplate.convertAndSend(config.queueName, "Hello from RabbitMQConfig!");
		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
	}
}