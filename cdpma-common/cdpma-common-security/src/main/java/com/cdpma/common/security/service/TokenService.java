package com.cdpma.common.security.service;

import com.cdpma.common.core.constant.CacheConstants;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.utils.JwtUtils;
import com.cdpma.common.core.utils.StringUtils;
import com.cdpma.common.core.utils.ip.IpUtils;
import com.cdpma.common.core.utils.uuid.IdUtils;
import com.cdpma.common.pojo.auth.SessionToken;
import com.cdpma.common.pojo.pojo.SysOperator;
import com.cdpma.common.pojo.pojo.SysUser;
import com.cdpma.common.redis.service.RedisService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TokenService {

    @Autowired
    private RedisService redisService;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final static long TOKEN_EXPIRE_TIME = CacheConstants.EXPIRATION;

    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    private final static Long TOKEN_REFRESH_THRESHOLD_MINUTES = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;

    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(SysOperator operator)
    {
        String token = IdUtils.fastUUID();
        SessionToken sessionToken = new SessionToken();
        sessionToken.setOperatorId(operator.getOperatorId());
        sessionToken.setOperatorName(operator.getOperatorName());
        sessionToken.setIpaddr(IpUtils.getIpAddr());
        sessionToken.setToken(token);
        refreshToken(sessionToken);

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(SecurityConstants.OPERATOR_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_OPERATOR_ID, operator.getOperatorId());
        claimsMap.put(SecurityConstants.DETAILS_OPERATOR_NAME, operator.getOperatorName());

        Map<String, Object> rspMap = new HashMap<String, Object>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", TOKEN_EXPIRE_TIME);
        return rspMap;

    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public SessionToken getsessionToken(String token)
    {
        SessionToken user = null;
        try
        {
            if (StringUtils.isNotEmpty(token))
            {
                String userkey = JwtUtils.getUserKey(token);
                user = redisService.getCacheObject(getTokenKey(userkey));
                return user;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return user;
    }

    /**
     * 刷新session Token
     */
    private void refreshToken(SessionToken sessionToken) {
        sessionToken.setLoginTime(System.currentTimeMillis());
        sessionToken.setExpireTime(sessionToken.getLoginTime() + TOKEN_EXPIRE_TIME * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(sessionToken.getToken());
        redisService.setCacheObject(userKey, sessionToken, TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    private String getTokenKey(String token)
    {
        return ACCESS_TOKEN + token;
    }

    /**
     * 延长token有效期
     */
    public void extendTokenExpireTime(String token) {
        System.out.println(token);
        redisService.expire(getTokenKey(token), TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    /**
     * 删除token
     */
    public void deleteToken(String userkey) {
        redisService.deleteObject(getTokenKey(userkey));
    }


}
