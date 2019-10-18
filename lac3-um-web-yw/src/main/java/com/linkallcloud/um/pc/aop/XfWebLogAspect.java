package com.linkallcloud.um.pc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import org.apache.dubbo.config.annotation.Reference;
import com.linkallcloud.web.busilog.BusiWebLogAspect;
import com.linkallcloud.um.domain.sys.XfWebBusiLog;
import com.linkallcloud.um.iapi.sys.ILacWebBusiLogManager;

@Aspect
@Component
@Order(5)
public class XfWebLogAspect extends BusiWebLogAspect<Long, XfWebBusiLog, ILacWebBusiLogManager> {

    @Reference(version = "${dubbo.service.version}", application = "${dubbo.application.id}")
	private ILacWebBusiLogManager lacWebBusiLogManager;

	@Override
	protected ILacWebBusiLogManager logService() {
		return lacWebBusiLogManager;
	}

	// @Pointcut("@annotation(com.linkallcloud.core.busilog.annotation.WebLog)")
	@Pointcut("execution(public * com.linkallcloud.um.pc.controller..*.*(..))")
	public void xfWebLog() {
	}

	@Pointcut("execution(* com.linkallcloud.web.controller.*.*(..))")
	public void webLog() {
	}

	@Override
	@Around("xfWebLog() || webLog()")
	public Object autoLog(ProceedingJoinPoint joinPoint) throws Throwable {
		return super.autoLog(joinPoint);
	}

}
