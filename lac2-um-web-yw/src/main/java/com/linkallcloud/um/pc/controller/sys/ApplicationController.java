package com.linkallcloud.um.pc.controller.sys;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.linkallcloud.busilog.annotation.Module;
import com.linkallcloud.busilog.annotation.WebLog;
import com.linkallcloud.comm.web.controller.BaseLController;
import com.linkallcloud.comm.web.utils.Controllers;
import com.linkallcloud.dto.AppVisitor;
import com.linkallcloud.dto.Result;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.Exceptions;
import com.linkallcloud.exception.IllegalParameterException;
import com.linkallcloud.pagination.Page;
import com.linkallcloud.pagination.WebPage;
import com.linkallcloud.query.Query;
import com.linkallcloud.query.rule.Equal;
import com.linkallcloud.um.domain.party.KhCompany;
import com.linkallcloud.um.domain.party.KhRole;
import com.linkallcloud.um.domain.party.YwRole;
import com.linkallcloud.um.domain.sys.Application;
import com.linkallcloud.um.iapi.party.IKhCompanyManager;
import com.linkallcloud.um.iapi.party.IKhRoleManager;
import com.linkallcloud.um.iapi.party.IYwRoleManager;
import com.linkallcloud.um.iapi.sys.IApplicationManager;
import com.linkallcloud.util.Domains;
import com.linkallcloud.www.ISessionUser;

@Controller
@RequestMapping(value = "/Application", method = RequestMethod.POST)
@Module(name = "应用")
public class ApplicationController extends BaseLController<Application, IApplicationManager> {

	@Reference(version = "${dubbo.service.version}", application = "${dubbo.application.id}")
	private IApplicationManager applicationManager;

	@Reference(version = "${dubbo.service.version}", application = "${dubbo.application.id}")
	private IYwRoleManager ywRoleManager;

	@Reference(version = "${dubbo.service.version}", application = "${dubbo.application.id}")
	private IKhRoleManager khRoleManager;

	@Reference(version = "${dubbo.service.version}", application = "${dubbo.application.id}")
	private IKhCompanyManager khCompanyManager;

	@Override
	protected IApplicationManager manager() {
		return applicationManager;
	}

	@Override
	protected String doEdit(boolean prepare, Long parentId, String parentClass, Long id, String uuid, Trace t,
			ModelMap modelMap, AppVisitor av) {
		return super.doEdit(true, parentId, parentClass, id, uuid, t, modelMap, av);
	}

	@RequestMapping(value = { "/saveInter" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@WebLog(db = true)
	@ResponseBody
	public Result<Object> saveInter(@RequestBody @Valid Application entity, Trace t, AppVisitor av) {
		try {
			Application dbApp = manager().fetchByIdUuid(t, entity.getId(), entity.getUuid());
			dbApp.setHost(entity.getHost());
			dbApp.setTimeout(entity.getTimeout());
			dbApp.setMessageEncAlg(entity.getMessageEncAlg());
			dbApp.setMessageEncKey(entity.getMessageEncKey());
			dbApp.setSignatureAlg(entity.getSignatureAlg());
			dbApp.setSignatureKey(entity.getSignatureKey());
			manager().updateInterfaceInfo(t, dbApp);
			return new Result<>(dbApp);
		} catch (Throwable e) {
			return Exceptions.makeErrorResult(e);
		}
	}

	@RequestMapping(value = "/list4KhCompany", method = RequestMethod.GET)
	public String list4KhCompany(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "uuid", required = false) String uuid, Trace t, ModelMap modelMap, AppVisitor av) {
		if (id != null && uuid != null) {
			KhCompany company = khCompanyManager.fetchByIdUuid(t, id, uuid);
			modelMap.put("khCompanyName", company.getName());
		}

		modelMap.put("companies", findApp4UserApp(t, av));

		modelMap.put("khCompanyId", id == null ? "" : id);
		modelMap.put("khCompanyUuid", uuid);
		return "page/Application/list4KhCompany";
	}

	private List<KhCompany> findApp4UserApp(Trace t, AppVisitor av) {
		Query query = new Query();
		query.addRule(new Equal("ywUserId", Long.parseLong(av.getId())));
		query.addRule(new Equal("appId", Long.parseLong(av.getAppId())));
		List<KhCompany> companies = khCompanyManager.find(t, query);
		return companies;
	}

	@RequestMapping(value = "/page4KhCompany", method = RequestMethod.GET)
	public @ResponseBody Result<Page<Long, Application>> page4KhCompany(@RequestBody WebPage webPage, Trace t,
			AppVisitor av) throws IllegalParameterException {
		Page<Long, Application> page = webPage.toPage();
		if (!page.hasRule4Field("khCompanyId") || !page.hasRule4Field("khCompanyUuid")) {
			throw new IllegalParameterException(Exceptions.CODE_ERROR_PARAMETER, "khCompanyId,khCompanyUuid参数错误。");
		}

		page.addRule(new Equal("ywUserId", Long.parseLong(av.getId())));
		page.addRule(new Equal("appId", Long.parseLong(av.getAppId())));

		page = manager().findPage4KhCompany(t, page);
		return new Result<>(page);
	}

	@RequestMapping(value = "/list4YwRole", method = RequestMethod.GET)
	public String list4YwRole(@RequestParam(value = "roleId", required = false) Long roleId,
			@RequestParam(value = "roleUuid", required = false) String roleUuid, Trace t, ModelMap modelMap) {
		if (roleId != null && roleUuid != null) {
			YwRole role = ywRoleManager.fetchByIdUuid(t, roleId, roleUuid);
			modelMap.put("roleName", role.getName());
		}

		ISessionUser su = Controllers.getSessionUser();
		List<YwRole> roles = ywRoleManager.findCompanyRoles(t, Long.parseLong(su.getCompanyId()), Domains.ROLE_NORMAL);
		modelMap.put("roles", roles);

		modelMap.put("roleId", roleId);
		modelMap.put("roleUuid", roleUuid);
		return "page/Application/list4YwRole";
	}

	@RequestMapping(value = "/page4YwRole", method = RequestMethod.GET)
	public @ResponseBody Result<Page<Long, Application>> page4YwRole(@RequestBody WebPage webPage, Trace t,
			AppVisitor av) throws IllegalParameterException {
		Page<Long, Application> page = webPage.toPage();
		if (!page.hasRule4Field("roleId") || !page.hasRule4Field("roleUuid")) {
			throw new IllegalParameterException(Exceptions.CODE_ERROR_PARAMETER, "roleId,roleUuid参数错误。");
		}
		page = manager().findPage4YwRole(t, page);
		return new Result<>(page);
	}

	// list4YwSysRole
	@RequestMapping(value = "/list4YwSysRole", method = RequestMethod.GET)
	public String list4YwSysRole(@RequestParam(value = "roleId", required = false) Long roleId,
			@RequestParam(value = "roleUuid", required = false) String roleUuid, Trace t, ModelMap modelMap) {
		if (roleId != null && roleUuid != null) {
			YwRole role = ywRoleManager.fetchByIdUuid(t, roleId, roleUuid);
			modelMap.put("roleName", role.getName());
		}

		ISessionUser su = Controllers.getSessionUser();
		List<YwRole> roles = ywRoleManager.findCompanyRoles(t, Long.parseLong(su.getCompanyId()), Domains.ROLE_SYSTEM);
		modelMap.put("roles", roles);

		modelMap.put("roleId", roleId);
		modelMap.put("roleUuid", roleUuid);
		return "page/Application/list4YwSysRole";
	}

	// page4YwSysRole
	@RequestMapping(value = "/page4YwSysRole", method = RequestMethod.GET)
	public @ResponseBody Result<Page<Long, Application>> page4YwSysRole(@RequestBody WebPage webPage, Trace t,
			AppVisitor av) throws IllegalParameterException {
		Page<Long, Application> page = webPage.toPage();
		if (!page.hasRule4Field("roleId") || !page.hasRule4Field("roleUuid")) {
			throw new IllegalParameterException(Exceptions.CODE_ERROR_PARAMETER, "roleId,roleUuid参数错误。");
		}
		page = manager().findPage4YwRole(t, page);
		return new Result<>(page);
	}

	// list4KhSysRole
	@RequestMapping(value = "/list4KhSysRole", method = RequestMethod.GET)
	public String list4KhSysRole(@RequestParam(value = "roleId", required = false) Long roleId,
			@RequestParam(value = "roleUuid", required = false) String roleUuid, Trace t, ModelMap modelMap) {
		if (roleId != null && roleUuid != null) {
			KhRole role = khRoleManager.fetchByIdUuid(t, roleId, roleUuid);
			modelMap.put("roleName", role.getName());
		}

		ISessionUser su = Controllers.getSessionUser();
		List<KhRole> roles = khRoleManager.findCompanyRoles(t, Long.parseLong(su.getCompanyId()), Domains.ROLE_SYSTEM);
		modelMap.put("roles", roles);

		modelMap.put("roleId", roleId);
		modelMap.put("roleUuid", roleUuid);
		return "page/Application/list4KhSysRole";
	}

	// page4KhSysRole
	@RequestMapping(value = "/page4KhSysRole", method = RequestMethod.GET)
	public @ResponseBody Result<Page<Long, Application>> page4KhSysRole(@RequestBody WebPage webPage, Trace t,
			AppVisitor av) throws IllegalParameterException {
		Page<Long, Application> page = webPage.toPage();
		if (!page.hasRule4Field("roleId") || !page.hasRule4Field("roleUuid")) {
			throw new IllegalParameterException(Exceptions.CODE_ERROR_PARAMETER, "roleId,roleUuid参数错误。");
		}
		page = manager().findPage4KhRole(t, page);
		return new Result<>(page);
	}

	@RequestMapping(value = "/list4SelfKhRole", method = RequestMethod.GET)
	public String list4SelfKhRole(@RequestParam(value = "roleId", required = false) Long roleId,
			@RequestParam(value = "roleUuid", required = false) String roleUuid, Trace t, ModelMap modelMap) {
		if (roleId != null && roleUuid != null) {
			KhRole role = khRoleManager.fetchByIdUuid(t, roleId, roleUuid);
			modelMap.put("roleName", role.getName());
		}

		ISessionUser su = Controllers.getSessionUser();
		modelMap.put("khCompanyId", Long.parseLong(su.getCompanyId()));

		List<KhRole> roles = khRoleManager.findCompanyRoles(t, Long.parseLong(su.getCompanyId()), Domains.ROLE_NORMAL);
		modelMap.put("roles", roles);

		modelMap.put("roleId", roleId);
		modelMap.put("roleUuid", roleUuid);
		return "page/Application/list4SelfKhRole";
	}

	@RequestMapping(value = "/page4SelfKhRole", method = RequestMethod.GET)
	public @ResponseBody Result<Page<Long, Application>> page4SelfKhRole(@RequestBody WebPage webPage, Trace t,
			AppVisitor av) throws IllegalParameterException {
		Page<Long, Application> page = webPage.toPage();
		if (!page.hasRule4Field("roleId") || !page.hasRule4Field("roleUuid")) {
			throw new IllegalParameterException(Exceptions.CODE_ERROR_PARAMETER, "roleId,roleUuid参数错误。");
		}
		page = manager().findPage4KhRole(t, page);
		return new Result<>(page);
	}

}
