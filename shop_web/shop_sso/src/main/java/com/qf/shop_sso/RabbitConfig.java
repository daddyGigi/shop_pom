package com.qf.shop_sso;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by  .Life on 2019/07/18/0018 20:01
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue getQueue(){
        return new Queue("email_queue");
    }
    @Bean
    public FanoutExchange getExchange(){
        return new FanoutExchange("email_exchange");
    }

    @Bean
    public Binding getBinding(Queue getQueue,FanoutExchange getExchange){
        return BindingBuilder.bind(getQueue).to(getExchange);
    }
}
