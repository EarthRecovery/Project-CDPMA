package com.cdpma.common.core.exception.auth;

import org.apache.commons.lang3.StringUtils;

public class NoPermissionException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public NoPermissionException(String message) {
        super(message);
    }
    public NoPermissionException(String[] permissions)
    {
        super(StringUtils.join(permissions, ","));
    }
}
