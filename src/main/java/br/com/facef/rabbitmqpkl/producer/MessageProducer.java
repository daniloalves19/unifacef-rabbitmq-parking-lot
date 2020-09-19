package br.com.facef.rabbitmqpkl.producer;

import static br.com.facef.rabbitmqpkl.configuration.DirectExchangeConfiguration.DIRECT_EXCHANGE_NAME;
import static br.com.facef.rabbitmqpkl.configuration.DirectExchangeConfiguration.ORDER_MESSAGES_QUEUE_NAME;
import static br.com.facef.rabbitmqpkl.configuration.DirectExchangeConfiguration.PRIMARY_ROUTING_KEY;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendFakeMessage() throws Exception  {

        do {
            log.info("Sending a fake message...");
            rabbitTemplate.convertAndSend(
                    DIRECT_EXCHANGE_NAME,
                    PRIMARY_ROUTING_KEY,
                    "FAKE-MESSAGE-PKL");

            Thread.sleep(30000);

        } while (true);
    }

}
