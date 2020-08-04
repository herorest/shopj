package com.boot.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLogAspect {

    public static final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);
    /**
     * AOP通知：
     * 1. 前置通知：在方法调用之前执行
     * 2. 后置通知：在方法正常调用之后执行
     * 3. 环绕通知：在方法调用之前和之后都分别可以执行的通知
     * 4. 异常通知：如果在方法调用过程中发生异常，则通知
     * 5. 最终通知：在方法调用之后通知
     */

    // 切面表达式
    // execution代表所要执行的表达式主体，
    // *代表方法返回类型为所有类型，
    // 包名代表aop监控的类所在的包，..代表该包及子包下所有类方法，*代表类名，*(..) 里的*代表类中的方法名，(..)表示方法中的任意参数
    @Around("execution(* com.boot.service.impl..*.*(..))")
    public Object recordTimelog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("===== 开始执行 {}.{} =====", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());

        // 记录开始时间
        long begin = System.currentTimeMillis();

        // 执行目标service
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long takeTime = end - begin;

        if(takeTime > 3000){
            log.error("===== 执行结束，耗时: {} 毫秒 =====", takeTime);
        }else{
            log.info("===== 执行结束，耗时: {} 毫秒 =====", takeTime);
        }

        return result;
    }
}
