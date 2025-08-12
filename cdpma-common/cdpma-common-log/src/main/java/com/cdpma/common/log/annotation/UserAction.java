package com.cdpma.common.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.cdpma.common.log.enums.UserActionType;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ElementType.METHOD })
public @interface UserAction {

    UserActionType value() default UserActionType.DEFAULT;

    boolean hasRule() default false;
}
