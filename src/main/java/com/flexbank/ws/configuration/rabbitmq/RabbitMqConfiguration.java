package com.flexbank.ws.configuration.rabbitmq;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Data
@NoArgsConstructor
public class RabbitMqConfiguration {

    private String exchange;
    private String internalTransferQueue;
    private String externalTransferQueue;
    private String approvedExternalTransferQueue;
    private String orderCardQueue;
    private String internalTransferRoutingKey;
    private String externalTransferRoutingKey;
    private String approvedExternalTransferRoutingKey;
    private String orderCardRoutingKey;

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue internalTransferQueue(){
        return new Queue(internalTransferQueue, true);
    }

    @Bean
    Queue externalTransferQueue() {
        return new Queue(externalTransferQueue, true);
    }

    @Bean
    Queue approvedExternalTransferQueue() {
        return new Queue(approvedExternalTransferQueue, true);
    }

    @Bean
    Queue orderCardQueue() {
        return new Queue(orderCardQueue, true);
    }

    @Bean
    Binding internalTransferBinding(Queue internalTransferQueue, DirectExchange exchange){
        return BindingBuilder.bind(internalTransferQueue)
                .to(exchange).with(internalTransferRoutingKey);
    }

    @Bean
    Binding externalTransferBinding(Queue externalTransferQueue, DirectExchange exchange){
        return BindingBuilder.bind(externalTransferQueue)
                .to(exchange).with(externalTransferRoutingKey);
    }

    @Bean
    Binding approvedExternalTransferBinding(Queue approvedExternalTransferQueue,
                                            DirectExchange exchange){
        return BindingBuilder.bind(approvedExternalTransferQueue)
                .to(exchange).with(approvedExternalTransferRoutingKey);
    }

    @Bean
    Binding orderCardBinding(Queue orderCardQueue, DirectExchange exchange){
        return BindingBuilder.bind(orderCardQueue)
                .to(exchange).with(orderCardRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
