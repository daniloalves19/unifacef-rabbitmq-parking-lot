package br.com.facef.rabbitmqpkl.configuration;
/*
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

 */
import org.springframework.amqp.core.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfiguration {

    public static final String DIRECT_EXCHANGE_NAME = "order-exchange";
    public static final String ORDER_MESSAGES_QUEUE_NAME = "order-messages-queue";
    public static final String ORDER_MESSAGES_QUEUE_DLQ_NAME = ORDER_MESSAGES_QUEUE_NAME + ".dlq";

    public static final String ORDER_MESSAGES_QUEUE_PARKING_LOT_NAME = ORDER_MESSAGES_QUEUE_NAME + ".pkl";
    public static final String PRIMARY_ROUTING_KEY = "primaryRoutingKey";

    @Bean
    Queue orderMessagesQueue() {

        /*
        return QueueBuilder.durable(ORDER_MESSAGES_QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", ORDER_MESSAGES_QUEUE_DLQ_NAME)
                .build();

         */



        return QueueBuilder.durable(ORDER_MESSAGES_QUEUE_NAME)
                .deadLetterExchange(DIRECT_EXCHANGE_NAME)
                .deadLetterRoutingKey(ORDER_MESSAGES_QUEUE_DLQ_NAME)
                .build();


    }

    @Bean
    Queue orderMessagesDeadLetterQueue() {
/*
        return QueueBuilder.durable(ORDER_MESSAGES_QUEUE_DLQ_NAME)
                .ttl(10000)
                .build();
*/


        return QueueBuilder.durable(ORDER_MESSAGES_QUEUE_DLQ_NAME)
                .deadLetterExchange(DIRECT_EXCHANGE_NAME)
                .deadLetterRoutingKey(PRIMARY_ROUTING_KEY)
                .ttl(10000)
                .build();


    }

    @Bean
    Queue orderMessagesParkingLotQueue() {
        return new Queue(ORDER_MESSAGES_QUEUE_PARKING_LOT_NAME);
    }

    @Bean
    DirectExchange exchange() {
        return ExchangeBuilder.directExchange(DIRECT_EXCHANGE_NAME).durable(true).build();
    }

    @Bean
    Binding bindingOrderMessagesQueue(Queue orderMessagesQueue, DirectExchange exchange) {
        //return BindingBuilder.bind(queue).to(exchange).with(ORDER_MESSAGES_QUEUE_NAME);
        return BindingBuilder.bind(orderMessagesQueue).to(exchange).with(PRIMARY_ROUTING_KEY);
    }

    @Bean
    Binding bindingOrderMessagesDeadLetterQueue(Queue orderMessagesDeadLetterQueue, DirectExchange exchange) {
        return BindingBuilder.bind(orderMessagesDeadLetterQueue).to(exchange).with(ORDER_MESSAGES_QUEUE_DLQ_NAME);

    }

    @Bean
    Binding bindingOrderMessagesParkingLotQueue( Queue orderMessagesParkingLotQueue, DirectExchange exchange) {
        return BindingBuilder.bind(orderMessagesParkingLotQueue).to(exchange).with(ORDER_MESSAGES_QUEUE_PARKING_LOT_NAME);
    }
}
