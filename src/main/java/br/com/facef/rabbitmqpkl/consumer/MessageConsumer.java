package br.com.facef.rabbitmqpkl.consumer;

import br.com.facef.rabbitmqpkl.configuration.DirectExchangeConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static br.com.facef.rabbitmqpkl.configuration.DirectExchangeConfiguration.DIRECT_EXCHANGE_NAME;

@Configuration
@Slf4j
@Component
public class MessageConsumer {

    //@Autowired
    private RabbitTemplate rabbitTemplate;

    public MessageConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = DirectExchangeConfiguration.ORDER_MESSAGES_QUEUE_NAME)
    public void processOrderMessage(Message message) throws Exception {
        log.info("Processing message: {}", message.toString());

        if (hasExceededRetryCount(message)) {
            putIntoParkingLot(message);
            return;
        }

        throw new Exception("Business Rule Exception");
    }

    private boolean hasExceededRetryCount(Message message) {
        List<Map<String, ?>> xDeathHeader = message.getMessageProperties().getXDeathHeader();
        if (xDeathHeader != null && xDeathHeader.size() >= 1) {
            Long count = (Long) xDeathHeader.get(0).get("count");
            return count >= 3;
        }

        return false;
    }

    private void putIntoParkingLot(Message failedMessage) {
        log.info("Tentativas sendo enviadas para Parking Lot");
/*
        this.rabbitTemplate.send(
                DirectExchangeConfiguration.ORDER_MESSAGES_QUEUE_PARKING_LOT_NAME,
                failedMessage);
*/
        this.rabbitTemplate.send(
                DirectExchangeConfiguration.ORDER_MESSAGES_QUEUE_PARKING_LOT_NAME,
                failedMessage);
    }
}