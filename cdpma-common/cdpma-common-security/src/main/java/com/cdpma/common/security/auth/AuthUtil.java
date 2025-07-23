package com.cdpma.common.security.auth;

import com.cdpma.common.security.annotation.Logical;
import com.cdpma.common.security.annotation.RequiresTags;

public class AuthUtil {

    /**
     * 底层的 AuthLogic 对象
     */
    public static AuthLogic authLogic = new AuthLogic();

    public static void checkTags(RequiresTags requiresTags)
    {
        authLogic.checkTags(requiresTags);
    }
}



