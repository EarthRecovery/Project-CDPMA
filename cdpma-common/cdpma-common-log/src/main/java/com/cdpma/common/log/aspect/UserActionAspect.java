package com.cdpma.common.log.aspect;

import com.cdpma.api.systemuser.RemoteLogService;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.log.annotation.Log;
import com.cdpma.common.log.annotation.UserAction;
import com.cdpma.common.log.enums.ActionStatus;
import com.cdpma.common.pojo.bo.SysNotificationBO;
import com.cdpma.common.pojo.enums.NotificationType;
import com.cdpma.common.pojo.pojo.SysLikeRecord;
import com.cdpma.common.pojo.pojo.SysOrder;
import com.cdpma.common.pojo.pojo.SysUser;
import com.cdpma.common.pojo.pojo.SysUserAction;
import com.cdpma.common.rabbitmq.config.RabbitMQConfig;
import com.cdpma.common.rabbitmq.handler.RabbitHandler;
import com.cdpma.common.security.utils.SecurityUtils;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class UserActionAspect {

    @Autowired
    private RemoteLogService remoteLogService;

    @Autowired
    private RabbitHandler rabbitHandler;

    /**
     * 处理请求前执行
     */
    @Before(value = "@annotation(userAction)")
    public void doBefore(JoinPoint joinPoint, UserAction userAction){

    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(userAction)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, UserAction userAction, Object jsonResult)
    {
        handleLog(joinPoint, userAction, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "@annotation(userAction)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, UserAction userAction, Exception e)
    {
        handleLog(joinPoint, userAction, e, null);
    }

    /**
     * 处理日志记录
     *
     * @param joinPoint 切点
     * @param userAction 日志注解
     * @param e 异常信息
     * @param jsonResult 返回结果
     */
    protected void handleLog(final JoinPoint joinPoint, UserAction userAction, final Exception e, Object jsonResult){
        //记录进用户日志
        SysUserAction sysUserAction = recordUserActionLog(joinPoint, userAction, e, jsonResult);
        Object args = Arrays.asList(joinPoint.getArgs());
        // 触发后续操作（进入MQ）
        rabbitHandler.sengMessage(RabbitMQConfig.RABBITMQ_EXCHANGE,
                RabbitMQConfig.RABBITMQ_USER_ACTION_ROUTING, sysUserAction,args);
    }

    protected SysUserAction recordUserActionLog(final JoinPoint joinPoint, UserAction userAction, final Exception e, Object jsonResult) {
        SysUserAction sysUserAction = new SysUserAction();
        if(e == null){
            sysUserAction.setActionStatus(ActionStatus.SUCCESS.ordinal());
            sysUserAction.setActionTime(new Date());
            sysUserAction.setActionType(userAction.value().name());
            sysUserAction.setOperatorId(SecurityUtils.getOperatorId());
            recordUserActionTarget(joinPoint, sysUserAction, userAction);
        }else{
            sysUserAction.setActionStatus(ActionStatus.FAIL.ordinal());
            sysUserAction.setActionTime(new Date());
            sysUserAction.setActionType(userAction.value().name());
            sysUserAction.setOperatorId(SecurityUtils.getOperatorId());
            recordUserActionTarget(joinPoint, sysUserAction, userAction);
        }

        remoteLogService.insertUserAction(sysUserAction, SecurityConstants.INNER);
        return sysUserAction;
    }

    protected void recordUserActionTarget(final JoinPoint joinPoint, SysUserAction sysUserAction, UserAction userAction) {
        String actionName = userAction.value().name();
        if (actionName.startsWith("LIKERECORD")) {
            SysLikeRecord sysLikeRecord = (SysLikeRecord) joinPoint.getArgs()[0];
            sysUserAction.setGoodId(sysLikeRecord.getGoodId());
        }
        if(actionName.startsWith("ORDER") && !actionName.startsWith("ORDER_ADD")) {
            Long orderId = (Long) joinPoint.getArgs()[0];
            sysUserAction.setOrderId(orderId);
        }
        if(actionName.startsWith("ORDER_ADD")){
            SysOrder sysOrder = (SysOrder) joinPoint.getArgs()[0];
            sysUserAction.setOrderId(sysOrder.getOrderId());
        }
    }


}
