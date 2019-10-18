package com.linkallcloud.um.pc.controller.party;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.dubbo.config.annotation.Reference;
import com.linkallcloud.core.busilog.annotation.Module;
import com.linkallcloud.core.dto.Result;
import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.dto.Tree;
import com.linkallcloud.core.exception.Exceptions;
import com.linkallcloud.core.exception.IllegalParameterException;
import com.linkallcloud.core.query.Query;
import com.linkallcloud.core.query.rule.Equal;
import com.linkallcloud.um.domain.party.YwRole;
import com.linkallcloud.um.domain.party.YwUser;
import com.linkallcloud.um.iapi.party.IYwRoleManager;
import com.linkallcloud.um.iapi.party.IYwUserManager;
import com.linkallcloud.core.util.Domains;

@Controller
@RequestMapping(value = "/YwSysRole", method = RequestMethod.POST)
@Module(name = "系统角色")
public class YwSysRoleController extends RoleController<YwRole, YwUser, IYwRoleManager, IYwUserManager> {

	@Reference(version = "${dubbo.service.version}", application = "${dubbo.application.id}")
	private IYwRoleManager ywRoleManager;

	@Reference(version = "${dubbo.service.version}", application = "${dubbo.application.id}")
	private IYwUserManager ywUserManager;

	@Override
	protected IYwRoleManager manager() {
		return ywRoleManager;
	}

	@Override
	protected IYwUserManager userManager() {
		return ywUserManager;
	}

	@Override
	protected String getMainPage() {
		return "page/party/YwSysRole/list";
	}

	@Override
	protected String getEditPage() {
		return "page/party/YwSysRole/edit";
	}

	@Override
	protected int getRoleType() {
		return Domains.ROLE_SYSTEM;
	}

	@RequestMapping(value = "/addApps", method = RequestMethod.GET)
	public @ResponseBody Result<Object> addApps(@RequestParam(value = "roleId", required = false) Long roleId,
			@RequestParam(value = "roleUuid", required = false) String roleUuid,
			@RequestBody Map<String, Long> appUuidIds, Trace t, ModelMap modelMap) {
		Boolean ret = ywRoleManager.addRoleApps(t, roleId, roleUuid, appUuidIds);
		return new Result<Object>(!ret, Exceptions.CODE_ERROR_UNKNOW, "保存失败");
	}

	@RequestMapping(value = "/removeApps", method = RequestMethod.GET)
	public @ResponseBody Result<Object> deleteApps(@RequestParam(value = "roleId", required = false) Long roleId,
			@RequestParam(value = "roleUuid", required = false) String roleUuid,
			@RequestBody Map<String, Long> appUuidIds, Trace t, ModelMap modelMap) {
		Boolean ret = ywRoleManager.removeRoleApps(t, roleId, roleUuid, appUuidIds);
		return new Result<Object>(!ret, Exceptions.CODE_ERROR_UNKNOW, "保存失败");
	}

	@RequestMapping(value = "/addUsers", method = RequestMethod.GET)
	public @ResponseBody Result<Object> addUsers(@RequestParam(value = "roleId", required = false) Long roleId,
			@RequestParam(value = "roleUuid", required = false) String roleUuid,
			@RequestBody Map<String, Long> userUuidIds, Trace t, ModelMap modelMap) {
		Boolean ret = ywRoleManager.addRoleUsers(t, roleId, roleUuid, userUuidIds);
		return new Result<Object>(!ret, Exceptions.CODE_ERROR_UNKNOW, "保存失败");
	}

	@RequestMapping(value = "/removeUsers", method = RequestMethod.GET)
	public @ResponseBody Result<Object> removeUsers(@RequestParam(value = "roleId", required = false) Long roleId,
			@RequestParam(value = "roleUuid", required = false) String roleUuid,
			@RequestBody Map<String, Long> userUuidIds, Trace t, ModelMap modelMap) {
		Boolean ret = ywRoleManager.removeRoleUsers(t, roleId, roleUuid, userUuidIds);
		return new Result<Object>(!ret, Exceptions.CODE_ERROR_UNKNOW, "保存失败");
	}

	@RequestMapping(value = "/getPermedMenuTree", method = RequestMethod.GET)
	public @ResponseBody Result<List<Tree>> getUmTree(@RequestParam(value = "roleId") Long roleId,
			@RequestParam(value = "roleUuid") String roleUuid, @RequestParam(value = "appId") Long appId,
			@RequestParam(value = "appUuid") String appUuid, Trace t) throws IllegalParameterException {
		List<Tree> items = ywRoleManager.findPermedMenus(t, null, roleId, appId);
		return new Result<>(items);
	}

	@RequestMapping(value = "/permSetup", method = RequestMethod.GET)
	public String permSetup(@RequestParam(value = "roleId", required = false) Long roleId,
			@RequestParam(value = "roleUuid", required = false) String roleUuid, Trace t, ModelMap modelMap) {
		if (roleId != null && roleUuid != null) {
			YwRole role = ywRoleManager.fetchByIdUuid(t, roleId, roleUuid);
			modelMap.put("roleName", role.getName());
		}

		Query query = new Query();
		query.addRule(new Equal("status", 0));
		query.addRule(new Equal("type", Domains.ROLE_SYSTEM));
		List<YwRole> roles = ywRoleManager.find(t, query);
		modelMap.put("roles", roles);

		modelMap.put("roleId", roleId);
		modelMap.put("roleUuid", roleUuid);

		return "page/party/YwSysRole/permSetup";
	}

	@RequestMapping(value = "/saveRoleAppMenuPerm", method = RequestMethod.GET)
	public @ResponseBody Result<Object> saveRoleAppMenuPerms(@RequestParam(value = "roleId") Long roleId,
			@RequestParam(value = "roleUuid") String roleUuid, @RequestParam(value = "appId") Long appId,
			@RequestParam(value = "appUuid") String appUuid, @RequestBody Map<String, Long> menuUuidIds, Trace t,
			ModelMap modelMap) {
		Boolean ret = ywRoleManager.saveRoleAppMenuPerm(t, roleId, roleUuid, appId, appUuid, menuUuidIds);
		return new Result<Object>(!ret, Exceptions.CODE_ERROR_UNKNOW, "保存失败");
	}

}
