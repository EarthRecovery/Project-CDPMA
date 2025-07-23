package com.cdpma.common.log.aspect;

import com.alibaba.fastjson2.JSON;
import com.cdpma.common.core.utils.StringUtils;
import com.cdpma.common.log.enums.BusinessStatus;
import com.cdpma.common.log.service.LogService;
import com.cdpma.common.pojo.pojo.SysRuntimeLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.cdpma.common.log.annotation.Log;

import java.util.Map;

@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /** 计算操作消耗时间 */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<Long>("Cost Time");

    @Autowired
    private LogService logService;

    /**
     * 处理请求前执行,记录开始时间
     */
    @Before(value = "@annotation(controllerLog)")
    public void doBefore(JoinPoint joinPoint, Log controllerLog)
    {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult)
    {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     * 这里只进行了无状态的演示，有状态的需要等到登陆功能完成才能写
     *
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e)
    {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult){
        try
        {
            // *========数据库日志=========*//
            SysRuntimeLog sysRuntimeLog = new SysRuntimeLog();
            sysRuntimeLog.setIsSuccessful(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
//            String ip = IpUtils.getIpAddr();
//            sysRuntimeLog.setOperatorIp(ip);
//            sysRuntimeLog.setOperUrl(StringUtils.substring(ServletUtils.getRequest().getRequestURI(), 0, 255));
//            String username = SecurityUtils.getUsername();
//            if (StringUtils.isNotBlank(username))
//            {
//                sysRuntimeLog.setOperName(username);
//            }

            if (e != null)
            {
                sysRuntimeLog.setIsSuccessful(BusinessStatus.FAIL.ordinal());
//                sysRuntimeLog.setErrorMsg(StringUtils.substring(Convert.toStr(e.getMessage(), ExceptionUtil.getExceptionMessage(e)), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            sysRuntimeLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
//            sysRuntimeLog.setRequestMethod(ServletUtils.getRequest().getMethod());
//            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, sysRuntimeLog, jsonResult);
            // 设置消耗时间
            sysRuntimeLog.setRuntimeDuration(System.currentTimeMillis() - TIME_THREADLOCAL.get());
//            // 保存数据库
//            asyncLogService.saveSysLog(sysRuntimeLog);
            logService.saveSysLog(sysRuntimeLog);
        }
        catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
        finally
        {
            TIME_THREADLOCAL.remove();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log 日志
     * @param sysRuntimeLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysRuntimeLog sysRuntimeLog, Object jsonResult) throws Exception
    {
        // 设置action动作
//        sysRuntimeLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
//        sysRuntimeLog.setTitle(log.title());
        // 设置操作人类别
//        sysRuntimeLog.(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData())
        {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, sysRuntimeLog, log.excludeParamNames());
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && StringUtils.isNotNull(jsonResult))
        {
            sysRuntimeLog.setResponseParameters(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param sysRuntimeLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysRuntimeLog sysRuntimeLog, String[] excludeParamNames) throws Exception
    {
//        Map<?, ?> paramsMap = ServletUtils.getParamMap(ServletUtils.getRequest());
//        String requestMethod = operLog.getRequestMethod();
//        if (StringUtils.isEmpty(paramsMap) && StringUtils.equalsAny(requestMethod, HttpMethod.PUT.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name()))
//        {
//            String params = argsArrayToString(joinPoint.getArgs(), excludeParamNames);
//            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
//        }
//        else
//        {
//            operLog.setOperParam(StringUtils.substring(JSON.toJSONString(paramsMap, excludePropertyPreFilter(excludeParamNames)), 0, 2000));
//        }
    }
}
