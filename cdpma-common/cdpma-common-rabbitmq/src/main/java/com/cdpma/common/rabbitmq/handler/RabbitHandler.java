package com.cdpma.common.rabbitmq.handler;

import com.cdpma.common.rabbitmq.constant.BaseDTO;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class RabbitHandler {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public <T> void sengMessage(String exchange, String routingKey, T object, Object args){
        BaseDTO<T> baseDTO = new BaseDTO<>();
        baseDTO.setData(object);
        baseDTO.setCreateTime(String.valueOf(new Date()));
        baseDTO.setId(UUID.randomUUID().toString());
        baseDTO.setArgs(args);
        rabbitTemplate.convertAndSend(exchange, routingKey, baseDTO);
    }

//    public <T> BaseDTO<T> receiveMessage(String queueName) {
//        Object message = rabbitTemplate.receiveAndConvert(queueName);
//        if (message instanceof BaseDTO) {
//            return (BaseDTO<T>) message;
//        }
//        return null;
//    }
}
