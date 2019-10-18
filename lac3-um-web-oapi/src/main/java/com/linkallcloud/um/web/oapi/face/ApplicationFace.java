package com.linkallcloud.um.web.oapi.face;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.dubbo.config.annotation.Reference;
import com.linkallcloud.core.busilog.annotation.Module;
import com.linkallcloud.web.face.annotation.Face;
import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.face.message.request.FaceRequest;
import com.linkallcloud.core.face.message.request.IdFaceRequest;
import com.linkallcloud.core.face.message.request.ListFaceRequest;
import com.linkallcloud.core.face.message.request.ObjectFaceRequest;
import com.linkallcloud.core.lang.Strings;
import com.linkallcloud.core.query.Query;
import com.linkallcloud.core.query.WebQuery;
import com.linkallcloud.core.query.rule.Equal;
import com.linkallcloud.um.domain.sys.Application;
import com.linkallcloud.um.iapi.sys.IApplicationManager;

@Controller
@RequestMapping(value = "/face/Application", method = RequestMethod.POST)
@Module(name = "应用")
public class ApplicationFace {

	@Reference(version = "${dubbo.service.version}", application = "${dubbo.application.id}")
	private IApplicationManager applicationManager;

	@Face(login = false)
	@RequestMapping(value = "/fetchById", method = RequestMethod.POST)
	public @ResponseBody Object fetchById(IdFaceRequest faceReq, Trace t) throws Exception {
		if (Strings.isBlank(faceReq.getId())) {
			return null;
		}
		Application app = applicationManager.fetchById(t, Long.parseLong(faceReq.getId()));
		if (isInnerVisitor(t, faceReq)) {
			return app;
		} else {
			return desensitization(faceReq, app);
		}
	}

	@Face(login = false)
	@RequestMapping(value = "/fetch", method = RequestMethod.POST)
	public @ResponseBody Object fetch(ObjectFaceRequest<String> faceReq, Trace t) throws Exception {
		String appCode = faceReq.getData();
		Application app = applicationManager.fetchByCode(t, appCode);
		if (isInnerVisitor(t, faceReq)) {
			return app;
		} else {
			return desensitization(faceReq, app);
		}
	}

	@Face(login = false)
	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
	public @ResponseBody Object findAll(ListFaceRequest faceReq, Trace t) throws Exception {
		if (isInnerVisitor(t, faceReq)) {
			Query q = new Query();
			q.addRule(new Equal("status", 0));
			return applicationManager.find(t, q);
		}
		return null;
	}

	@Face(login = false)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public @ResponseBody Object find(ListFaceRequest faceReq, Trace t) throws Exception {
		if (isInnerVisitor(t, faceReq)) {
			Query q = new Query();
			WebQuery wq = faceReq.getQuery();
			if (wq != null) {
				q = wq.toQuery();
			}
			return applicationManager.find(t, q);
		}
		return null;
	}

	/**
	 * 判断是否内部应用访问
	 * 
	 * @param t
	 * @param faceReq
	 * @return
	 */
	private boolean isInnerVisitor(Trace t, FaceRequest faceReq) {
		String visitorAppCode = faceReq.getAppCode();
		Application visitorApp = applicationManager.fetchByCode(t, visitorAppCode);
		if (visitorApp != null && visitorApp.getType() == 0) {// 内部应用
			return true;
		}
		return false;
	}

	/**
	 * 访问者appcode匹配，并脱敏
	 * 
	 * @param faceReq
	 * @param app
	 * @return
	 */
	private Application desensitization(FaceRequest faceReq, Application app) {
		if (app != null && app.getCode().equals(faceReq.getAppCode())) {
			app.desensitization();
		}
		return app;
	}

}
