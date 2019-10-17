package com.linkallcloud.um.server.aop;

import com.linkallcloud.busilog.BusiServiceLogAspect;
import com.linkallcloud.service.IServiceBusiLogService;
import com.linkallcloud.um.domain.sys.XfServiceBusiLog;
import com.linkallcloud.um.server.service.sys.ILacServiceBusiLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(5)
public class XfBusiServiceLogAspect extends BusiServiceLogAspect<Long, XfServiceBusiLog, ILacServiceBusiLogService> {

    @Autowired
    private ILacServiceBusiLogService xfServiceBusiLogService;

    @Override
    protected IServiceBusiLogService<Long, XfServiceBusiLog> logService() {
        return xfServiceBusiLogService;
    }

    // @Pointcut("@annotation(com.linkallcloud.busilog.annotation.ServLog)")
    @Pointcut("execution(public * com.linkallcloud.um.server.manager..*.*(..))")
    public void manager() {
    }

    @Pointcut("execution(* com.linkallcloud.manager.*.*(..))")
    public void manager2() {
    }

    @Override
    @Around("manager() || manager2()")
    public Object autoLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return super.autoLog(joinPoint);
    }

}