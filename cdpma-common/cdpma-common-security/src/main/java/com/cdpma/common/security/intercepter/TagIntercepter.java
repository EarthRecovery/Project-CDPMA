package com.cdpma.common.security.intercepter;

import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.utils.ServletUtils;
import com.cdpma.common.security.context.TagContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

@Component
public class TagIntercepter implements AsyncHandlerInterceptor {

    @Autowired
    private TagContext tagContext;

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request,
                             javax.servlet.http.HttpServletResponse response,
                             Object handler) throws Exception {

        String fromInternal = request.getHeader(SecurityConstants.FROM_SOURCE);
        if (SecurityConstants.INNER.equalsIgnoreCase(fromInternal)) {
            return true; // 内部调用直接放行
        }

        Long ID = Long.valueOf(ServletUtils.getHeader(request, SecurityConstants.DETAILS_OPERATOR_ID));
        tagContext.getTags(ID);

        return true;
    }
}
