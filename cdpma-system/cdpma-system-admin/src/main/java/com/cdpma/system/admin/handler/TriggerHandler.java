package com.cdpma.system.admin.handler;

import com.cdpma.common.core.utils.RuleUtils;
import com.cdpma.common.pojo.dto.OperationMapperDTO;
import com.cdpma.common.pojo.dto.ResponseMapperDTO;
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

    public void trigger(SysUserAction action, Object args) throws Exception {
        // 处理触发逻辑
        // 这里可以添加具体的业务逻辑代码

        // 查看是否对应操作触发条件
        Map<OperationMapperDTO, List<ResponseMapperDTO>> operationMap = sysOperationService.getOperationMap();

        Boolean isTrigger = false;
        // 这里可以满足多个触发条件的情况
        for(Map.Entry<OperationMapperDTO, List<ResponseMapperDTO>> entry : operationMap.entrySet()) {
            if (Objects.equals(entry.getKey().getConditionName(), action.getActionType())) {
                // 检查条件是否满足
                if(entry.getKey().getRule() != null){
                    if(!RuleUtils.meetRule(entry.getKey().getRule(), args)) continue;
                }
                // 执行相应的操作
                for(ResponseMapperDTO value : entry.getValue()) {
                    startUpExecutor.execute(value.getResponseName(), args, action, value.getParam());
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
