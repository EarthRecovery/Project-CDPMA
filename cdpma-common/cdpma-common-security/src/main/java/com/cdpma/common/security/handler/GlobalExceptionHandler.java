package com.cdpma.common.security.handler;

import com.cdpma.common.core.constant.HttpStatus;
import com.cdpma.common.core.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import com.cdpma.common.core.exception.auth.NoPermissionException;

/**
 * 全局异常类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoPermissionException.class)
    public AjaxResult handleNotPermissionException(NoPermissionException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        return AjaxResult.error(HttpStatus.FORBIDDEN, e.getMessage());
    }
}
