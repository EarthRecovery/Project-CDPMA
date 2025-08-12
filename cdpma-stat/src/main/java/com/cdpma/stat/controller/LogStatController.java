package com.cdpma.stat.controller;

import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.stat.service.LogStatService;
import com.cdpma.stat.service.UserStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/logstat")
public class LogStatController {

    @Autowired
    LogStatService logStatService;

    @GetMapping("/timeCost")
    private AjaxResult map() {
        Map<String, Double> result = logStatService.getMapData();
        return AjaxResult.success(result);
    }

    @GetMapping("/refresh")
    private AjaxResult refresh() {
        // 清除缓存
        Map<String, Double> result = logStatService.refresh();
        return AjaxResult.success(result);
    }
}
