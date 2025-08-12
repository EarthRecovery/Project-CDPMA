package com.cdpma.system.admin.consumer;

import com.cdpma.system.admin.handler.TriggerHandler;
import com.cdpma.common.pojo.pojo.SysUserAction;
import com.cdpma.common.rabbitmq.config.RabbitMQConfig;
import com.cdpma.common.rabbitmq.constant.BaseDTO;
import com.cdpma.common.rabbitmq.handler.RabbitHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class UserActionConsumer {
    @Autowired
    RabbitHandler rabbitHandler;

    @Autowired
    TriggerHandler triggerHandler;

    @RabbitListener(queues = RabbitMQConfig.RABBITMQ_USER_ACTION_TOPIC, containerFactory = "rabbitListenerContainerFactory")
    public void handleUserAction(Message message) throws Exception {
        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructParametricType(BaseDTO.class, SysUserAction.class);
        BaseDTO<SysUserAction> dto = mapper.readValue(json, type);
        SysUserAction sysUserAction = dto.getData();
        Object args = dto.getArgs();
        triggerHandler.trigger(sysUserAction, args);
    }

}
