package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @ClassName AutoFillAspect
 * @Author t1533
 * @Date 2025/3/6 11:16
 * @Description 公共字段自动填充
 * @Version 1.0.0
 */
@Slf4j
@Component
@Aspect
public class AutoFillAspect {

    //定义切入点
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    //定义前置通知
    @Before(value = "autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException {
        log.info("公共字段自动填充");
        //获取AutoFill注解值(INSERT,UPDATE)
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType value = annotation.value();
        //获取对应方法参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) return;
        Object entity = args[0];
        //获取服务端日期和操作员ID
        LocalDateTime now = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();
        //判断是INSERT还是UPDATE
        if (OperationType.INSERT == value) {
            try {
                Method setCreateTime = args[0].getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setUpdateTime = args[0].getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setCreateUser = args[0].getClass().getDeclaredMethod("setCreateTime", Long.class);
                Method setUpdateUser = args[0].getClass().getDeclaredMethod("setCreateTime", Long.class);
                //通过反射为对象属性赋值
                setCreateTime.invoke(entity, now);
                setUpdateTime.invoke(entity, now);
                setCreateUser.invoke(entity, id);
                setUpdateUser.invoke(entity, id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (OperationType.UPDATE == value) {
            try {
                Method setUpdateTime = args[0].getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = args[0].getClass().getDeclaredMethod("setUpdateUser", Long.class);
                //通过反射为对象属性赋值
                setUpdateUser.invoke(entity, id);
                setUpdateTime.invoke(entity,now);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
