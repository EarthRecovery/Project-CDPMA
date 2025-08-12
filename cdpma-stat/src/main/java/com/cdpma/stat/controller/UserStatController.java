package com.cdpma.stat.controller;

import com.cdpma.common.core.web.controller.BaseController;
import com.cdpma.common.core.web.domain.AjaxResult;
import com.cdpma.stat.service.UserStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/userstat")
public class UserStatController extends BaseController {

    @Autowired
    UserStatService userStatService;

    @GetMapping("/map")
    private AjaxResult map() {
        Map<String, Integer> result = userStatService.getMapData();
        return AjaxResult.success(result);
    }

    @GetMapping("/refresh")
    private AjaxResult refresh() {
        // 清除缓存
        Map<String, Integer> result = userStatService.refresh();
        return AjaxResult.success(result);
    }

}
