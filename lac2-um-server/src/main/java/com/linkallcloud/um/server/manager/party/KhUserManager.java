package com.linkallcloud.um.server.manager.party;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.linkallcloud.busilog.annotation.Module;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.pagination.Page;
import com.linkallcloud.um.domain.party.KhCompany;
import com.linkallcloud.um.domain.party.KhRole;
import com.linkallcloud.um.domain.party.KhUser;
import com.linkallcloud.um.domain.sys.Application;
import com.linkallcloud.um.iapi.party.IKhUserManager;
import com.linkallcloud.um.server.service.party.IKhCompanyService;
import com.linkallcloud.um.server.service.party.IKhRoleService;
import com.linkallcloud.um.server.service.party.IKhUserService;

@Service(interfaceClass = IKhUserManager.class, version = "${dubbo.service.version}")
@Component
@Module(name = "用户")
public class KhUserManager
		extends UserManager<KhUser, IKhUserService, KhRole, IKhRoleService, KhCompany, IKhCompanyService>
		implements IKhUserManager {

	@Autowired
	private IKhUserService khUserService;

	@Autowired
	private IKhRoleService khRoleService;

	@Autowired
	private IKhCompanyService khCompanyService;

	@Override
	protected IKhUserService service() {
		return khUserService;
	}

	@Override
	protected IKhRoleService roleService() {
		return khRoleService;
	}

	@Override
	protected IKhCompanyService companyService() {
		return khCompanyService;
	}

	@Override
	public Page<Long, KhUser> findSelfUserPage(Trace t, Page<Long, KhUser> page) {
		return service().findSelfUserPage(t, page);
	}

	@Override
	public Page<Long, KhUser> findPermedSelfUserPage(Trace t, Page<Long, KhUser> page) {
		return service().findPermedSelfUserPage(t, page);
	}

	@Override
	protected List<Application> findApplicaitons4User(Trace t, Long userId) {
		return applicationService.find4KhUser(t, userId);
	}

}
