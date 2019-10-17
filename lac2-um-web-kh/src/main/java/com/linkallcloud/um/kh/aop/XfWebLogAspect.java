package com.linkallcloud.um.kh.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.linkallcloud.comm.web.busilog.BusiWebLogAspect;
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

	// @Pointcut("@annotation(com.linkallcloud.busilog.annotation.WebLog)")
	@Pointcut("execution(public * com.linkallcloud.um.kh.controller..*.*(..))")
	public void xfWebLog() {
	}

	@Pointcut("execution(* com.linkallcloud.comm.web.controller.*.*(..))")
	public void webLog() {
	}

	@Override
	@Around("xfWebLog() || webLog()")
	public Object autoLog(ProceedingJoinPoint joinPoint) throws Throwable {
		return super.autoLog(joinPoint);
	}

}
