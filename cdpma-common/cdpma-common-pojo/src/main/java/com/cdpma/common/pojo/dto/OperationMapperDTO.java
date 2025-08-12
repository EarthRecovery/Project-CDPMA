package com.cdpma.common.pojo.dto;

import java.util.List;

public class OperationMapperDTO {

    private String rule;
    private String conditionName;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperationMapperDTO)) return false;

        OperationMapperDTO that = (OperationMapperDTO) o;

        if (!rule.equals(that.rule)) return false;
        return conditionName.equals(that.conditionName);
    }

    @Override
    public String toString() {
        return "OperationMapperDTO{" +
                "rule='" + rule + '\'' +
                ", conditionName='" + conditionName + '\'' +
                '}';
    }
}
