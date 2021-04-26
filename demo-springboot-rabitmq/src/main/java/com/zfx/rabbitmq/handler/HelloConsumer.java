package com.zfx.rabbitmq.handler;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component//默认持久化  非独占
public class HelloConsumer {

//    @RabbitHandler
//    @RabbitListener(queuesToDeclare = @Queue("hello"))
//    public void receive(String msg){
//        System.out.println("msg: "+msg);
//    }
}
