package com.cdpma.stat.service;

import com.cdpma.api.systemuser.RemoteLogService;
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
public class LogStatService {

    @Autowired
    RemoteLogService remoteLogService;

    @Autowired
    RedisService redisService;
    public static final String REDIS_KEY = "timecost_stat_data";


    public Map<String, Double> getMapData() {

        if(redisService.hasKey(REDIS_KEY)){
            // 如果缓存中有数据，则直接从缓存中获取
            Map<String, Double> cachedData = redisService.getCacheMap(REDIS_KEY);
            return cachedData;
        }else{
            return refresh();
        }
    }

    public Map<String, Double> refresh() {
        AjaxResult ajaxResult = remoteLogService.getRuntimeLogStat(SecurityConstants.INNER);
        List<Map<String, Object>> list = (List<Map<String, Object>>) ajaxResult.get(AjaxResult.DATA_TAG);
        // 将获取到的数据存入缓存
        Map<String, Double> map = new HashMap<>();
        for (Map<String, Object> item : list) {
            String title = (String) item.get("title");
            Double count = (Double) item.get("count");
            map.put(title, count);
        }
        redisService.setCacheMap(REDIS_KEY, map);
        redisService.expire(REDIS_KEY, 1800); // 设置缓存过期时间为30分钟
        return map;
    }
}
