package com.cdpma.common.security.intercepter;

import com.cdpma.api.systemuser.RemoteTagService;
import com.cdpma.common.core.utils.ServletUtils;
import com.cdpma.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cdpma.common.core.context.SecurityContextHolder;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.utils.StringUtils;
import com.cdpma.common.security.utils.SecurityUtils;

@Component
public class HeadInterceptor implements AsyncHandlerInterceptor {

//    @Autowired
//    @Lazy
//    private RemoteTagService remoteTagService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (!(handler instanceof HandlerMethod))
        {
            return true;
        }

        String fromInternal = request.getHeader(SecurityConstants.FROM_SOURCE);
        if (SecurityConstants.INNER.equalsIgnoreCase(fromInternal)) {
            return true; // 内部调用直接放行
        }

        SecurityContextHolder.setOperatorId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_OPERATOR_ID));
        SecurityContextHolder.setOperatorName(ServletUtils.getHeader(request, SecurityConstants.DETAILS_OPERATOR_NAME));
        SecurityContextHolder.setOperatorKey(ServletUtils.getHeader(request, SecurityConstants.OPERATOR_KEY));

        // 获取operatorID 对应的tags
//        Long operatorId = SecurityContextHolder.getOperatorId();
//        AjaxResult ajaxResult = remoteTagService.getTagsByOperatorID(operatorId, SecurityConstants.INNER);
//        String[] tags = (String[]) ajaxResult.get(AjaxResult.DATA_TAG);
//        SecurityContextHolder.setTags(tags);

        return true;
    }
}
