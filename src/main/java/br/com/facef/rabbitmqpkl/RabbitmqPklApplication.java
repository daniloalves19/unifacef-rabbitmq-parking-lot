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

	//implements CommandLineRunner


	//@Autowired
	//private MessageProducer messageProducer;
	@Autowired
	RabbitTemplate rabbitTemplate;

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqPklApplication.class, args);
	}


	@EventListener(ApplicationReadyEvent.class)
	public void runningAfterStartup() throws Exception {
		log.info("Running method after startup to send messages!");

		do {
			rabbitTemplate.convertAndSend(
					DIRECT_EXCHANGE_NAME,
					PRIMARY_ROUTING_KEY,
					"FAKE-MESSAGE-PKL");
			Thread.sleep(30000);
		} while (true);




		//messageProducer.sendFakeMessage();
	}


/*
	@Override
	public void run(String... args) throws Exception {
		//messageProducer.sendFakeMessage();

		do {
            rabbitTemplate.convertAndSend(
                    DIRECT_EXCHANGE_NAME,
                    ORDER_MESSAGES_QUEUE_NAME,
                    "FAKE-MESSAGE-PKL");
			Thread.sleep(60000);
		} while (true);

	}

 */
}