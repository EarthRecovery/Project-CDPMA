package com.cdpma.system.admin.executor;

import com.cdpma.api.systemuser.RemoteNotificationService;
import com.cdpma.common.pojo.pojo.SysUserAction;
import com.cdpma.system.admin.constant.ClassNameConstant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Component
public class StartUpExecutor {

    @Autowired
    private ApplicationContext applicationContext;

    public void execute(String target, Object args, SysUserAction sysUserAction, String param) {
        List<String> targetList = Arrays.asList(target.split(":"));
        if (targetList.size() < 2) {
            throw new IllegalArgumentException("Invalid target format. Expected format: 'className:methodName'");
        }
        String className = ClassNameConstant.EXECUTOR_CLASS_NAME + targetList.get(0);
        String methodName = targetList.get(1);
        Class<?> clazz;
        try{
            clazz = Class.forName(className);
        }catch (ClassNotFoundException e){
            throw new RuntimeException("Class not found: " + className, e);
        }

        Class<?>[] paramTypes = new Class[3];
        paramTypes[0] = Object.class;
        paramTypes[1] = SysUserAction.class;
        paramTypes[2] = String.class;

        Object bean = applicationContext.getBean(clazz);
        Method method;
        try{
            method = clazz.getMethod(methodName, paramTypes);
        }catch (NoSuchMethodException e){
            throw new RuntimeException("Method not found: " + methodName + " in class " + className, e);
        }

        try{
            method.invoke(bean,(Object) args, sysUserAction, param);
        }catch(Exception e){
            throw new RuntimeException("Failed to invoke method: " + methodName + " in class " + className, e);
        }
    }
}
