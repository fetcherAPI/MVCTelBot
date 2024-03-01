package org.example.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import static org.example.model.RabbitQueue.ANSWER_MESSAGE_UPDATE;
import static org.example.model.RabbitQueue.DOC_MESSAGE_UPDATE;
import static org.example.model.RabbitQueue.PHOTO_MESSAGE_UPDATE;
import static org.example.model.RabbitQueue.TEXT_MESSAGE_UPDATE;

@Configuration
public class RabbitConfiguration {
    @Bean
    public MessageConverter jsonMessageController() {return new Jackson2JsonMessageConverter();}
    @Bean
    public Queue textMessageQueue() {return new Queue(TEXT_MESSAGE_UPDATE);}
    @Bean
    public Queue docMessageQueue() {return new Queue(DOC_MESSAGE_UPDATE);}
    @Bean
    public Queue photoMessageQueue() {return new Queue(PHOTO_MESSAGE_UPDATE);}
    @Bean
    public Queue answerMessageQueue() {return new Queue(ANSWER_MESSAGE_UPDATE);}
}
