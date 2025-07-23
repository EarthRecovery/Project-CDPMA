package com.cdpma.common.security.handler;

import com.cdpma.common.core.constant.HttpStatus;
import com.cdpma.common.core.exception.ServiceException;
import com.cdpma.common.core.utils.StringUtils;
import com.cdpma.common.core.web.domain.AjaxResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import com.cdpma.common.core.exception.auth.NoPermissionException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request)
    {
        Integer code = e.getCode();
        return StringUtils.isNotNull(code) ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
    }

    /**
     * 注入参数异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("error", "Bad Request");

        // 收集所有字段的错误信息，格式: 字段名: 错误消息
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));

        response.put("message", errors);

        return AjaxResult.error(HttpStatus.BAD_REQUEST, errors);
    }
}
