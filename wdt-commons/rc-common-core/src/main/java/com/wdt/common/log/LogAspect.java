package com.wdt.common.log;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * Description:
 * Author: admin
 * Date: 2024/1/2
 */
@Slf4j
@Aspect
public class LogAspect {

    @Pointcut("@annotation(com.wdt.common.log.Log)")
    public void myPointCut(){}

    @Around("myPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;


        HttpServletRequest request =  attributes.getRequest();
        // 前置通知：记录请求相关信息
        log.info("url={}, method={}, ip={}, class_method={}, args={}",
                request.getRequestURI(), request.getMethod(), request.getRemoteAddr(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                joinPoint.getArgs());

        Object result;
        try {
            // 执行目标方法
            result = joinPoint.proceed();
            // 后置通知：记录方法调用结果
            HttpServletResponse response = attributes.getResponse();
            assert response != null;
            int status = response.getStatus();
            String statusStr = (status == 200) ? "成功" : "失败";

            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            Log funLog = method.getAnnotation(Log.class);
            String value = (funLog != null) ? funLog.value() : "";

            log.info("{}-----{}   调用结果：{}", new Date(), value, statusStr);
        } catch (Throwable e) {
            // 异常通知：记录异常信息
            log.error("发生异常：", e);
            throw e;
        }

        return result;
    }





}
