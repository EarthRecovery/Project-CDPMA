package com.cdpma.common.security.context;

import com.cdpma.api.systemuser.RemoteTagService;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.context.SecurityContextHolder;
import com.cdpma.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequestScope
public class TagContext {

    @Autowired
    private RemoteTagService remoteTagService;

    private Long[] tags;

    public Long[] getTags(Long operatorId) {
        if(SecurityContextHolder.getTags() == null || SecurityContextHolder.getTags().length == 0) {
            AjaxResult result = remoteTagService.getTagsByOperatorID(operatorId, SecurityConstants.INNER);
            // 先强制转成 List<?>，然后转换成 List<Long>
            List<?> rawList = (List<?>) result.get(AjaxResult.DATA_TAG);
            List<Long> listTags = rawList.stream()
                    .map(item -> {
                        if (item instanceof Number) {
                            return ((Number) item).longValue();
                        } else {
                            // 这里可以抛异常或者返回null，根据业务处理
                            return null;
                        }
                    })
                    .filter(Objects::nonNull) // 过滤掉null，防止空指针
                    .collect(Collectors.toList());

            tags = listTags.toArray(new Long[0]);
            SecurityContextHolder.setTags(tags);
        }else{
            tags = SecurityContextHolder.getTags();
        }
        return tags;
    }
}
