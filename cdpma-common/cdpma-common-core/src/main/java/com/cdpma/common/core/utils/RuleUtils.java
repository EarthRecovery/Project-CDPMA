package com.cdpma.common.core.utils;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.util.Map;

public class RuleUtils {

    //根据rule 判断是否满足
    // 判断标准为 输入的args 中的值是否满足某个条件
    // rule结构：
    /**
     * 第几个arg, arg类名，属性名（表达式），对比值 | 第几个arg, arg类名，属性名（表达式），对比值 | AND/OR
     * "0,quantity>3 | 0,unitPrice>20 | AND"
     * 如果arg就是一个参数，则使用$$占位
     * "0,$$>20 | AND"
     */
    public static boolean meetRule(String rule, Object argsObj) throws Exception {
        List<?> args = (List<?>) argsObj;
        String[] parts = rule.split("\\|");

        String connector = parts[parts.length - 1].trim();
        boolean isAnd = "AND".equalsIgnoreCase(connector);
        boolean isOr = "OR".equalsIgnoreCase(connector);
        int conditionCount = (isAnd || isOr) ? parts.length - 1 : parts.length;

        List<Boolean> results = new ArrayList<>();

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        if (engine == null) {
            throw new IllegalStateException("JavaScript engine not found. Use GraalJS or Nashorn library.");
        }

        for (int i = 0; i < conditionCount; i++) {
            String[] tokens = parts[i].trim().split(",", 3);

            int argIndex = Integer.parseInt(tokens[0].trim());
            Object argValue = args.get(argIndex);
            String expression;

            if (tokens.length == 2) {
                // 格式：0,$$>20
                expression = tokens[1].trim();
            } else {
                // 格式：0,field>3
                expression = tokens[1].trim();
            }

            Bindings bindings = engine.createBindings();

            if (argValue instanceof Map) {
                Map<?, ?> mapValue = (Map<?, ?>) argValue;
                for (Map.Entry<?, ?> entry : mapValue.entrySet()) {
                    bindings.put(entry.getKey().toString(), entry.getValue());
                }
            } else if (argValue instanceof Number || argValue instanceof String) {
                if (expression.contains("$$")) {
                    if (argValue instanceof String) {
                        expression = expression.replace("$$", "'" + argValue + "'");
                    } else {
                        expression = expression.replace("$$", argValue.toString());
                    }
                } else {
                    bindings.put("value", argValue);
                }
            } else {
                throw new IllegalArgumentException("Unsupported arg type: " + argValue.getClass().getName());
            }

            boolean match = (boolean) engine.eval(expression, bindings);
            results.add(match);
        }

        if (isAnd) {
            return results.stream().allMatch(Boolean::booleanValue);
        } else if (isOr) {
            return results.stream().anyMatch(Boolean::booleanValue);
        } else {
            return !results.isEmpty() && results.get(0);
        }
    }
}
