package com.cdpma.stat.service;

import com.cdpma.api.systemuser.RemoteOperatorService;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserStatService {

    @Autowired
    RemoteOperatorService remoteOperatorService;

    public Map<String, Integer> getMapData() {
        AjaxResult ajaxResult = remoteOperatorService.getOperatorStatData(SecurityConstants.INNER);
        List<Map<String, Object>> list = (List<Map<String, Object>>) ajaxResult.get(AjaxResult.DATA_TAG);
        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Object> row : list) {
            String region = (String) row.get("region");
            Integer count = ((Number) row.get("count")).intValue();
            result.put(region, count);
        }
        return result;
    }

}
