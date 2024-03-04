package org.studyonline.learning.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: RabbitMQ Config
 * @Author: Chengguang Li
 * @Date: 01/03/2024 2:45 pm
 */
@Slf4j
@Configuration
public class PayNotifyConfig {

    //switch
    public static final String PAYNOTIFY_EXCHANGE_FANOUT = "paynotify_exchange_fanout";
    //Payment result notification message type
    public static final String MESSAGE_TYPE = "payresult_notify";
    //Payment notification queue
    public static final String PAYNOTIFY_QUEUE = "paynotify_queue";

    //Declare the switch and make it persistent
    @Bean(PAYNOTIFY_EXCHANGE_FANOUT)
    public FanoutExchange paynotify_exchange_fanout() {
        // Three parameters: switch name, whether it is persistent, and whether it is automatically deleted when no queue is bound to it.
        return new FanoutExchange(PAYNOTIFY_EXCHANGE_FANOUT, true, false);
    }
    //Payment notification queue and persistence
    @Bean(PAYNOTIFY_QUEUE)
    public Queue course_publish_queue() {
        return QueueBuilder.durable(PAYNOTIFY_QUEUE).build();
    }

    //Exchange and payment notification queue binding
    @Bean
    public Binding binding_course_publish_queue(@Qualifier(PAYNOTIFY_QUEUE) Queue queue, @Qualifier(PAYNOTIFY_EXCHANGE_FANOUT) FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

}
