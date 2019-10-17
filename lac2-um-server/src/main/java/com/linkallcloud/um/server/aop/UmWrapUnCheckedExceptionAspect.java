package com.linkallcloud.um.server.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.linkallcloud.exception.BaseException;
import com.linkallcloud.exception.BaseRuntimeException;
import com.linkallcloud.exception.WrapUnCheckedExceptionAspect;

@Aspect
@Component
@Order(-999)
public class UmWrapUnCheckedExceptionAspect extends WrapUnCheckedExceptionAspect {

    @Pointcut("execution(public * com.linkallcloud.um.server.manager..*.*(..))")
    public void manager() {
    }

    @Pointcut("execution(* com.linkallcloud.manager.*.*(..))")
    public void manager2() {
    }

    @Override
    @Around("manager() || manager2()")
    public Object wrapException(ProceedingJoinPoint joinPoint) throws Throwable {
        return super.wrapException(joinPoint);
    }

}
