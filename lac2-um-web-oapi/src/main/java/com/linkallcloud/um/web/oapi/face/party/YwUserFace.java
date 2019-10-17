package com.linkallcloud.um.web.oapi.face.party;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.linkallcloud.busilog.annotation.Module;
import com.linkallcloud.comm.web.face.annotation.Face;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.face.message.request.IdFaceRequest;
import com.linkallcloud.face.message.request.ListFaceRequest;
import com.linkallcloud.face.message.request.ObjectFaceRequest;
import com.linkallcloud.face.message.request.PageFaceRequest;
import com.linkallcloud.lang.Strings;
import com.linkallcloud.pagination.Page;
import com.linkallcloud.query.WebQuery;
import com.linkallcloud.um.domain.party.YwUser;
import com.linkallcloud.um.iapi.party.IYwUserManager;

@Controller
@RequestMapping(value = "/face/YwUser", method = RequestMethod.POST)
@Module(name = "运维用户")
public class YwUserFace {

	@Reference(version = "${dubbo.service.version}", application = "${dubbo.application.id}")
	private IYwUserManager ywUserManager;

	@Face(login = false)
	@RequestMapping(value = "/fetchById", method = RequestMethod.POST)
	public @ResponseBody Object fetchById(IdFaceRequest faceReq, Trace t) throws Exception {
		if (Strings.isBlank(faceReq.getId())) {
			return null;
		}
		YwUser user = ywUserManager.fetchById(t, Long.parseLong(faceReq.getId()));
		user.desensitization();
		return user;
	}

	@Face(login = false)
	@RequestMapping(value = "/fetchByGovCode", method = RequestMethod.POST)
	public @ResponseBody Object fetchByGovCode(ObjectFaceRequest<String> faceReq, Trace t) throws Exception {
		String govCode = faceReq.getData();
		if (!Strings.isBlank(govCode)) {
			YwUser user = ywUserManager.fetchByGovCode(t, govCode);
			user.desensitization();
			return user;
		}
		return null;
	}

	@Face(login = false)
	@RequestMapping(value = "/fecthByAccount", method = RequestMethod.POST)
	public @ResponseBody Object fecthByAccount(ObjectFaceRequest<String> faceReq, Trace t) throws Exception {
		String a = faceReq.getData();
		if (!Strings.isBlank(a)) {
			YwUser user = ywUserManager.fecthByAccount(t, a);
			user.desensitization();
			return user;
		}
		return null;
	}

	@Face(login = false)
	@RequestMapping(value = "/findByMobile", method = RequestMethod.POST)
	public @ResponseBody Object findByMobile(ObjectFaceRequest<String> faceReq, Trace t) throws Exception {
		String a = faceReq.getData();
		if (!Strings.isBlank(a)) {
			List<YwUser> users = ywUserManager.findByMobile(t, a);
			desensitization(users);
			return users;
		}
		return null;
	}

	@Face(login = false)
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public @ResponseBody Object find(ListFaceRequest faceReq, Trace t) throws Exception {
		WebQuery wq = faceReq.getQuery();
		if (wq != null) {
			List<YwUser> users = ywUserManager.find(t, wq.toQuery());
			desensitization(users);
			return users;
		}
		return null;
	}

	@Face(login = false)
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	public @ResponseBody Object findPage(PageFaceRequest faceReq, Trace t) throws Exception {
		Page<Long, YwUser> page = new Page<>(faceReq);
		page = ywUserManager.findPage(t, page);
		desensitization(page.getData());
		return page;
	}

	private void desensitization(List<YwUser> users) {
		if (users != null && users.size() > 0) {
			for (YwUser user : users) {
				user.desensitization();
			}
		}
	}

}
