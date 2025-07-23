package com.cdpma.common.security.auth;

import com.cdpma.common.core.exception.auth.NoPermissionException;
import com.cdpma.common.pojo.enums.Tag;
import com.cdpma.common.security.annotation.Logical;
import com.cdpma.common.security.annotation.RequiresTags;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AuthLogic {

    /**
     * 验证用户是否含有指定权限
     *
     * @param requiresTags 权限注解
     *
     */
    public void checkTags(RequiresTags requiresTags)
    {
        if (requiresTags.logical() == Logical.AND)
        {
            checkTagsAnd(requiresTags.value());
        }
        else
        {
            checkTagsOr(requiresTags.value());
        }
    }

    /**
     * 验证用户是否含有指定权限，所有权限都必须包含
     *
     * @param Tags 权限码数组
     *
     */
    public void checkTagsAnd(int... Tags)
    {
        Set<Integer> tagsList = getTagsList();
        if(isAdmin(tagsList))
        {
            return; // 如果是管理员，则直接返回
        }
        for (Integer tags : Tags)
        {
            if(!hasTag(tagsList, tags)){
                throw new NoPermissionException("没有权限访问该资源，请联系管理员！");
            }
        }
    }

    /**
     * 验证用户是否含有指定权限，只需包含其中一个
     *
     * @param tags 权限码数组
     */
    public void checkTagsOr(int... tags)
    {
        Set<Integer> tagList = getTagsList();
        if(isAdmin(tagList))
        {
            return; // 如果是管理员，则直接返回
        }
        for (int tag : tags)
        {
            if(hasTag(tagList, tag))
            {
                return; // 如果有一个权限匹配，则直接返回
            }
        }
        if (tags.length > 0)
        {
            throw new NoPermissionException("没有权限访问该资源，请联系管理员！");
        }
    }

    /**
     * 获取当前用户的权限列表
     *
     * @return 权限列表
     */
    public Set<Integer> getTagsList()
    {
//        try
//        {
//            LoginUser loginUser = getLoginUser();
//            return loginUser.getPermissions();
//        }
//        catch (Exception e)
//        {
//            return new HashSet<>();
//        }
        HashSet<Integer> tags = new HashSet<>(); //测试数据，以后要换成接口
        tags.add(Tag.USER);
        return tags;
    }

    /**
     * 检查用户是否拥有指定权限
     * @param tags
     * @param tag4check
     * @return
     */
    private boolean hasTag(Set<Integer> tags, int tag4check)
    {
        for(Integer t : tags)
        {
            if(t == tag4check)
            {
                return true; // 如果有一个权限匹配，则直接返回
            }
        }
        return false;
    }

    /**
     * 检查用户是否拥有管理员权限
     * @param tags
     * @return
     */
    private boolean isAdmin(Set<Integer> tags)
    {
        for(Integer t : tags)
        {
            if(t == Tag.ADMIN)
            {
                return true; // 如果有一个权限匹配，则直接返回
            }
        }
        return false;
    }
}
