package com.cdpma.system.admin.handler;

import com.cdpma.common.pojo.pojo.SysOperation;
import com.cdpma.common.pojo.pojo.SysOperationTriggerCondition;
import com.cdpma.common.pojo.pojo.SysUserAction;
import com.cdpma.system.admin.executor.StartUpExecutor;
import com.cdpma.system.admin.service.impl.SysOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class TriggerHandler {

    @Autowired
    private SysOperationService sysOperationService;

    @Autowired
    private StartUpExecutor startUpExecutor;

    public void trigger(SysUserAction action, Object args) {
        // 处理触发逻辑
        // 这里可以添加具体的业务逻辑代码

        // 查看是否对应操作触发条件
        Map<String, List<String>> operationMap = sysOperationService.getOperationMap();

        Boolean isTrigger = false;
        // 这里可以满足多个触发条件的情况
        for(Map.Entry<String, List<String>> entry : operationMap.entrySet()) {
            // 检查条件是否满足
            if (Objects.equals(entry.getKey(), action.getActionType())) {
                // 执行相应的操作
                for(String value : entry.getValue()) {
                    startUpExecutor.execute(value, args, action);
                }
                // 成功触发
                isTrigger = true;
            }
        }

        if(!isTrigger) {
            System.out.println("无触发");
            System.out.println(operationMap.toString());
            return; // 没有找到触发条件，默认不触发
        }
    }
}
