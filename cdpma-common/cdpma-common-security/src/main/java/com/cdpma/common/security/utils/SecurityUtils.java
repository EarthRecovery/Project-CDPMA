package com.cdpma.common.security.utils;

import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.constant.TokenConstants;
import com.cdpma.common.core.context.SecurityContextHolder;
import com.cdpma.common.core.utils.ServletUtils;
import com.cdpma.common.core.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {
    /**
     * 获取用户名称
     */
    public static String getOperatorName()
    {
        return SecurityContextHolder.getOperatorName();
    }

    /**
     * 获取用户ID
     */
    public static Long getOperatorId(){
        return SecurityContextHolder.getOperatorId();
    }

    /**
     * 获取用户Tags
     */
    public static String[] getOperatorTags()
    {
        return SecurityContextHolder.getTags();
    }

    /**
     * 获取请求token
     */
    public static String getToken()
    {
        return getToken(ServletUtils.getRequest());
    }

    /**
     * 根据request获取请求token
     */
    public static String getToken(HttpServletRequest request)
    {
        // 从header获取token标识
        String token = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        return replaceTokenPrefix(token);
    }

    /**
     * 裁剪token前缀
     */
    public static String replaceTokenPrefix(String token)
    {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX))
        {
            token = token.replaceFirst(TokenConstants.PREFIX, "");
        }
        return token;
    }
}
