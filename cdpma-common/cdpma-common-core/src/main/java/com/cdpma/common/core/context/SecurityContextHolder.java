package com.cdpma.common.core.context;
import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.text.Convert;
import com.cdpma.common.core.utils.StringUtils;

/**
 * 获取当前线程变量中的 用户id、用户名称、Token等信息
 * 注意： 必须在网关通过请求头的方法传入，同时在HeaderInterceptor拦截器设置值。 否则这里无法获取
 *
 * @author ruoyi
 */
public class SecurityContextHolder
{
    private static final TransmittableThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void set(String key, Object value)
    {
        Map<String, Object> map = getLocalMap();
        map.put(key, value == null ? StringUtils.EMPTY : value);
    }

    public static String get(String key)
    {
        Map<String, Object> map = getLocalMap();
        return Convert.toStr(map.getOrDefault(key, StringUtils.EMPTY));
    }

    public static String[] getStringArray(String key) {
        Object val = getLocalMap().get(key);
        return val instanceof String[] ? (String[]) val : new String[0];
    }

    public static <T> T get(String key, Class<T> clazz)
    {
        Map<String, Object> map = getLocalMap();
        return StringUtils.cast(map.getOrDefault(key, null));
    }

    public static Map<String, Object> getLocalMap()
    {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null)
        {
            map = new ConcurrentHashMap<String, Object>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void setLocalMap(Map<String, Object> threadLocalMap)
    {
        THREAD_LOCAL.set(threadLocalMap);
    }

    public static Long getOperatorId()
    {
        return Convert.toLong(get(SecurityConstants.DETAILS_OPERATOR_ID), 0L);
    }

    public static void setOperatorId(String account)
    {
        set(SecurityConstants.DETAILS_OPERATOR_ID, account);
    }

    public static String getOperatorName()
    {
        return get(SecurityConstants.DETAILS_OPERATOR_NAME);
    }

    public static void setOperatorName(String Operatorname)
    {
        set(SecurityConstants.DETAILS_OPERATOR_NAME, Operatorname);
    }

    public static String getOperatorKey()
    {
        return get(SecurityConstants.OPERATOR_KEY);
    }

    public static void setOperatorKey(String OperatorKey)
    {
        set(SecurityConstants.OPERATOR_KEY, OperatorKey);
    }

    public static String[] getTags()
    {
        return getStringArray(SecurityConstants.OPERATOR_TAGS);
    }

    public static void setTags(String[] tags)
    {
        set(SecurityConstants.OPERATOR_TAGS, tags);
    }

    public static void remove()
    {
        THREAD_LOCAL.remove();
    }
}