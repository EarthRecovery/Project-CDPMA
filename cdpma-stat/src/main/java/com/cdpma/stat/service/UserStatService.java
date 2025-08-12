package com.cdpma.stat.service;

import com.cdpma.api.systemuser.RemoteOperatorService;
import com.cdpma.common.core.constant.SecurityConstants;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserStatService {

    @Autowired
    RemoteOperatorService remoteOperatorService;

    @Autowired
    RedisService redisService;

    public static final String REDIS_KEY = "user_stat_data";

    public Map<String, Integer> getMapData() {

        if(redisService.hasKey(REDIS_KEY)){
            // 如果缓存中有数据，则直接从缓存中获取
            Map<String, Integer> cachedData = redisService.getCacheMap(REDIS_KEY);
            return cachedData;
        }else{
            return refresh();
        }
    }

    public Map<String, Integer> refresh() {
        AjaxResult ajaxResult = remoteOperatorService.getOperatorStatData(SecurityConstants.INNER);
        List<Map<String, Object>> list = (List<Map<String, Object>>) ajaxResult.get(AjaxResult.DATA_TAG);
        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Object> row : list) {
            String region = (String) row.get("region");
            Integer count = ((Number) row.get("count")).intValue();
            result.put(region, count);
        }
        // 将获取到的数据存入缓存
        redisService.setCacheMap(REDIS_KEY, result);
        redisService.expire(REDIS_KEY, 1800); // 设置缓存过期时间为30分钟
        return result;
    }
}
