package com.cdpma.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 角色认证：必须具有指定角色标识才能进入该方法
 *
 * @author ruoyi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RequiresTags
{
    /**
     * 需要校验的角色标签
     */
    int[] value() default 0;

    /**
     * 验证逻辑：AND | OR，默认AND
     */
    Logical logical() default Logical.AND;
}