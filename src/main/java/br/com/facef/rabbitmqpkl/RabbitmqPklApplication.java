package br.com.facef.rabbitmqpkl;

import br.com.facef.rabbitmqpkl.producer.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import static br.com.facef.rabbitmqpkl.configuration.DirectExchangeConfiguration.DIRECT_EXCHANGE_NAME;
import static br.com.facef.rabbitmqpkl.configuration.DirectExchangeConfiguration.ORDER_MESSAGES_QUEUE_NAME;
import static br.com.facef.rabbitmqpkl.configuration.DirectExchangeConfiguration.PRIMARY_ROUTING_KEY;

@SpringBootApplication
@Slf4j
public class RabbitmqPklApplication  {

	@Autowired
	RabbitTemplate rabbitTemplate;

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqPklApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runningAfterStartup() throws Exception {
		log.info("Início do envio das mensagens!");

		do {
			rabbitTemplate.convertAndSend(
					DIRECT_EXCHANGE_NAME,
					PRIMARY_ROUTING_KEY,
					"FAKE-MESSAGE-PKL");
			Thread.sleep(30000);
		} while (true);
	}
}