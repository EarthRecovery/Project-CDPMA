package com.cdpma.common.log.aspect;

import com.alibaba.fastjson2.JSON;
import com.cdpma.common.core.context.SecurityContextHolder;
import com.cdpma.common.core.text.Convert;
import com.cdpma.common.core.utils.ExceptionUtil;
import com.cdpma.common.core.utils.ServletUtils;
import com.cdpma.common.core.utils.StringUtils;
import com.cdpma.common.core.utils.ip.IpUtils;
import com.cdpma.common.log.enums.BusinessStatus;
import com.cdpma.common.log.enums.OperatorType;
import com.cdpma.common.log.filter.PropertyPreExcludeFilter;
import com.cdpma.common.log.service.LogService;
import com.cdpma.common.pojo.enums.OperatorRoles;
import com.cdpma.common.pojo.pojo.SysRuntimeLog;
import com.cdpma.common.security.context.TagContext;
import com.cdpma.common.security.utils.SecurityUtils;
import org.apache.commons.lang3.ArrayUtils;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /** 计算操作消耗时间 */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<Long>("Cost Time");

    @Autowired
    private LogService logService;

    @Autowired
    TagContext tagContext;

    private boolean isSaveRequestData;

    private boolean isSaveResponseData;

    /** 排除敏感属性字段 */
    public static final String[] EXCLUDE_PROPERTIES = { "password"};

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
            SysRuntimeLog sysRuntimeLog = new SysRuntimeLog();
            getControllerMethodDescription(joinPoint, controllerLog, sysRuntimeLog, jsonResult);
            setRequestValue(joinPoint, sysRuntimeLog, controllerLog.excludeParamNames());
            //3. method
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            sysRuntimeLog.setMethod(className + "." + methodName + "()");
            //4. request method
            sysRuntimeLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            //5. operator type
            setOperatorType(sysRuntimeLog, SecurityUtils.getOperatorId());
            //6. operator name
            sysRuntimeLog.setOperatorName(SecurityUtils.getOperatorName());
            //7. url
            sysRuntimeLog.setOperationUrl(StringUtils.substring(ServletUtils.getRequest().getRequestURI(), 0, 255));
            //8. ip
            sysRuntimeLog.setOperatorIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
            //8.5 id
            sysRuntimeLog.setOperationId(SecurityContextHolder.getOperatorId());
            //10. json result
            if (isSaveResponseData && StringUtils.isNotNull(jsonResult))
            {
                sysRuntimeLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
            }

            // error code and message
            if (e != null)
            {
                sysRuntimeLog.setStatus(BusinessStatus.FAIL.ordinal());
                sysRuntimeLog.setErrorMsg(StringUtils.substring(Convert.toStr(e.getMessage(), ExceptionUtil.getExceptionMessage(e)), 0, 2000));
            }else{
                sysRuntimeLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            }
            //cost time
            sysRuntimeLog.setCostTime(System.currentTimeMillis() - TIME_THREADLOCAL.get());
            // 生成事件
            sysRuntimeLog.setCreateTime(new Date());

            // 存入数据库
            logService.saveSysLog(sysRuntimeLog);
        }
        catch (Exception exp)
        {
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
        // 1. title
        sysRuntimeLog.setTitle(log.title());
        //2. businessType
        sysRuntimeLog.setBusinessType(log.businessType().ordinal());
        this.isSaveRequestData = log.isSaveRequestData();
        this.isSaveResponseData = log.isSaveResponseData();

    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param sysRuntimeLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysRuntimeLog sysRuntimeLog, String[] excludeParamNames) throws Exception
    {
        //9. set params
        Map<?, ?> paramsMap = ServletUtils.getParamMap(ServletUtils.getRequest());
        String requestMethod = sysRuntimeLog.getRequestMethod();
        if (StringUtils.isEmpty(paramsMap) && StringUtils.equalsAny(requestMethod, HttpMethod.PUT.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name()))
        {
            String params = argsArrayToString(joinPoint.getArgs(), excludeParamNames);
            sysRuntimeLog.setOperationParam(StringUtils.substring(params, 0, 2000));
        }
        else
        {
            sysRuntimeLog.setOperationParam(StringUtils.substring(JSON.toJSONString(paramsMap, excludePropertyPreFilter(excludeParamNames)), 0, 2000));
        }

    }

    private void setOperatorType(SysRuntimeLog sysRuntimeLog, Long operatorId) {
        String[] tags = tagContext.getTags(operatorId);
        int operatorType = OperatorType.UNKNOWN.ordinal();
        for(String tag : tags){
            if(Objects.equals(tag, OperatorRoles.ADMIN)) operatorType = OperatorType.ADMIN.ordinal();
            if(Objects.equals(tag, OperatorRoles.ASSISTANT)) operatorType = OperatorType.ASSISTANT.ordinal();
            if(Objects.equals(tag, OperatorRoles.USER)) operatorType = OperatorType.USER.ordinal();
        }
        sysRuntimeLog.setOperatorType(operatorType);
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray, String[] excludeParamNames)
    {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0)
        {
            for (Object o : paramsArray)
            {
                if (StringUtils.isNotNull(o) && !isFilterObject(o))
                {
                    try
                    {
                        String jsonObj = JSON.toJSONString(o, excludePropertyPreFilter(excludeParamNames));
                        params += jsonObj.toString() + " ";
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 忽略敏感属性
     */
    public PropertyPreExcludeFilter excludePropertyPreFilter(String[] excludeParamNames)
    {
        return new PropertyPreExcludeFilter().addExcludes(ArrayUtils.addAll(EXCLUDE_PROPERTIES, excludeParamNames));
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o)
    {
        Class<?> clazz = o.getClass();
        if (clazz.isArray())
        {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        else if (Collection.class.isAssignableFrom(clazz))
        {
            Collection collection = (Collection) o;
            for (Object value : collection)
            {
                return value instanceof MultipartFile;
            }
        }
        else if (Map.class.isAssignableFrom(clazz))
        {
            Map map = (Map) o;
            for (Object value : map.entrySet())
            {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
