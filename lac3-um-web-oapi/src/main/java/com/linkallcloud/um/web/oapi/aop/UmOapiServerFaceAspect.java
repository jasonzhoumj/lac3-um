package com.linkallcloud.um.web.oapi.aop;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.linkallcloud.comm.web.face.FaceAspect;
import com.linkallcloud.comm.web.face.processor.PackageJsonProcessor;
import com.linkallcloud.comm.web.face.processor.PackageXmlProcessor;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.query.Query;
import com.linkallcloud.query.rule.Equal;
import com.linkallcloud.security.MsgSignObject;
import com.linkallcloud.um.domain.sys.Application;
import com.linkallcloud.um.iapi.sys.IApplicationManager;

@Aspect
@Component
@Order(2)
public class UmOapiServerFaceAspect extends FaceAspect {

	@Reference(version = "${dubbo.service.version}", application = "${dubbo.application.id}")
	private IApplicationManager applicationManager;

	@Pointcut("execution(public * com.linkallcloud.um.web.oapi.face..*.*(..))")
	public void oapiface() {
	}

	@Around("oapiface()")
	@Override
	public Object checkFace(ProceedingJoinPoint joinPoint) throws Throwable {
		return super.checkFace(joinPoint);
	}

	@Override
	protected PackageJsonProcessor getPackageJsonProcessor() {
		PackageJsonProcessor pjp = new PackageJsonProcessor();
		List<Application> apps = findApplications();
		if (apps != null && !apps.isEmpty()) {
			for (Application app : apps) {
				MsgSignObject mso = new MsgSignObject(app.getCode(), app.getSignatureAlg(), app.getSignatureKey(),
						app.getMessageEncAlg(), app.getMessageEncKey());
				mso.setTimeout(app.getTimeout() < 10 ? 10 : app.getTimeout());
				pjp.addMsgSignObject(app.getCode(), mso);
			}
		}
		return pjp;
	}

	@Override
	protected PackageXmlProcessor getPackageXmlProcessor() {
		return null;
	}

	private List<Application> findApplications() {
		Query query = new Query();
		query.addRule(new Equal("status", 0));
		return applicationManager.find(new Trace(true), query);
	}

}
