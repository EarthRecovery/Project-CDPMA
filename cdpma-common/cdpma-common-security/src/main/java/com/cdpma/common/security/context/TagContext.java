package com.cdpma.common.security.context;

import com.cdpma.api.systemuser.RemoteTagService;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.context.SecurityContextHolder;
import com.cdpma.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class TagContext {

    @Autowired
    private RemoteTagService remoteTagService;

    private String[] tags;

    public String[] getTags(Long operatorId) {
        if(SecurityContextHolder.getTags() == null) {
            AjaxResult result = remoteTagService.getTagsByOperatorID(operatorId, SecurityConstants.INNER);
            tags = (String[]) result.get(AjaxResult.DATA_TAG);
            SecurityContextHolder.setTags(tags);
        }else{
            tags = SecurityContextHolder.getTags();
        }
        return tags;
    }
}
