package com.cdpma.gateway.filter;

import com.cdpma.common.security.service.TokenService;
import com.cdpma.common.core.constant.CacheConstants;
import com.cdpma.common.core.constant.HttpStatus;
import com.cdpma.common.core.constant.TokenConstants;
import com.cdpma.common.core.utils.JwtUtils;
import com.cdpma.common.core.utils.ServletUtils;
import com.cdpma.common.core.utils.StringUtils;
import com.cdpma.common.redis.service.RedisService;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.gateway.config.IgnoreWhiteListProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IgnoreWhiteListProperties ignoreWhiteListProperties;

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();

        // 白名单路径直接放行
        for (String whitePath : ignoreWhiteListProperties.getWhites()) {
            System.out.println("白名单路径: " + whitePath);
            if (Objects.equals(whitePath, request.getURI().getPath())) {
                return chain.filter(exchange);
            }
        }

        String token = getToken(request);
        if (StringUtils.isEmpty(token))
        {
            return unauthorizedResponse(exchange, "令牌不能为空");
        }

        Claims claims = null;

        try {
            claims = JwtUtils.parseToken(token);
        } catch (SignatureException e) {
            return unauthorizedResponse(exchange, "令牌错误");
        }

        if (claims == null)
        {
            return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
        }
        String userkey = JwtUtils.getUserKey(claims);
        boolean islogin = redisService.hasKey(getTokenKey(userkey));
        if (!islogin)
        {
            return unauthorizedResponse(exchange, "登录状态已过期");
        }
        String userid = JwtUtils.getUserId(claims);
        String username = JwtUtils.getUserName(claims);
        if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username))
        {
            return unauthorizedResponse(exchange, "令牌验证失败");
        }

        // 设置用户信息到请求
        addHeader(mutate, SecurityConstants.OPERATOR_KEY, userkey);
        addHeader(mutate, SecurityConstants.DETAILS_OPERATOR_ID, userid);
        addHeader(mutate, SecurityConstants.DETAILS_OPERATOR_NAME, username);
        // 内部请求来源参数清除
        removeHeader(mutate, SecurityConstants.FROM_SOURCE);
        ServerHttpRequest mutatedRequest = mutate.build();
        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
        // 更新Session Token 有效期
        tokenService.extendTokenExpireTime(userkey);
        return chain.filter(mutatedExchange );
    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value)
    {
        if (value == null)
        {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = ServletUtils.urlEncode(valueStr);
        mutate.header(name, valueEncode);
    }

    private void removeHeader(ServerHttpRequest.Builder mutate, String name)
    {
        mutate.headers(httpHeaders -> httpHeaders.remove(name)).build();
    }


    /**
     * 获取请求token
     */
    private String getToken(ServerHttpRequest request)
    {
        String token = request.getHeaders().getFirst(SecurityConstants.AUTHORIZATION_HEADER);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX))
        {
            token = token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
        }
        return token;
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg)
    {
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), msg, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 获取缓存key
     */
    private String getTokenKey(String token)
    {
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }

    @Override
    public int getOrder() {
        return -200;
    }
}
