package com.cdpma.system.admin.consumer;

import com.cdpma.system.admin.handler.TriggerHandler;
import com.cdpma.common.pojo.pojo.SysUserAction;
import com.cdpma.common.rabbitmq.config.RabbitMQConfig;
import com.cdpma.common.rabbitmq.constant.BaseDTO;
import com.cdpma.common.rabbitmq.handler.RabbitHandler;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserActionConsumer {
    @Autowired
    RabbitHandler rabbitHandler;

    @Autowired
    TriggerHandler triggerHandler;

    @RabbitListener(queues = RabbitMQConfig.RABBITMQ_USER_ACTION_TOPIC)
    public void handleUserAction(BaseDTO<SysUserAction> dto) {
        SysUserAction sysUserAction = dto.getData();
        Object args = dto.getArgs();
        triggerHandler.trigger(sysUserAction, args);
    }

}
