package com.cdpma.common.rabbitmq.constant;
import org.aspectj.lang.JoinPoint;

public class BaseDTO<T> {
    private String id;

    private String createTime;

    private T data;

    private Object args;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getArgs() {
        return args;
    }

    public void setArgs(Object args) {
        this.args = args;
    }
}
