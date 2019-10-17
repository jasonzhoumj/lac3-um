package com.linkallcloud.um.server.manager.party;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.linkallcloud.busilog.annotation.Module;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.BaseRuntimeException;
import com.linkallcloud.pagination.Page;
import com.linkallcloud.um.domain.party.YwCompany;
import com.linkallcloud.um.domain.party.YwRole;
import com.linkallcloud.um.domain.party.YwUser;
import com.linkallcloud.um.domain.sys.Application;
import com.linkallcloud.um.iapi.party.IYwUserManager;
import com.linkallcloud.um.server.service.party.IYwCompanyService;
import com.linkallcloud.um.server.service.party.IYwRoleService;
import com.linkallcloud.um.server.service.party.IYwUserService;

@Service(interfaceClass = IYwUserManager.class, version = "${dubbo.service.version}")
@Component
@Module(name = "用户")
public class YwUserManager
		extends UserManager<YwUser, IYwUserService, YwRole, IYwRoleService, YwCompany, IYwCompanyService>
		implements IYwUserManager {

	@Autowired
	private IYwUserService ywUserService;

	@Autowired
	private IYwRoleService ywRoleService;

	@Autowired
	private IYwCompanyService ywCompanyService;

	@Override
	protected IYwUserService service() {
		return ywUserService;
	}

	@Override
	protected IYwRoleService roleService() {
		return ywRoleService;
	}

	@Override
	protected IYwCompanyService companyService() {
		return ywCompanyService;
	}

	@Override
	public Page<Long, YwUser> findPermedUserPage(Trace t, Page<Long, YwUser> page) {
		return service().findPermedUserPage(t, page);
	}

	@Override
	protected List<Application> findApplicaitons4User(Trace t, Long userId) {
		return applicationService.find4YwUser(t, userId);
	}

	// @CacheEvict(value = "YwUser", key = "\"ID-\" + #entity.id")
	@Override
	public boolean update(Trace t, YwUser entity) throws BaseRuntimeException {
		return super.update(t, entity);
	}

	@Override
	public YwUser findByMobileAndDdStatus(Trace t, String mobile) {
		return service().findByMobileAndDdStatus(t, mobile);
	}
	
	@Override
	public void cleanOtherUserMobileByUserId(Trace t, String mobile, Long userId) {
		service().cleanOtherUserMobileByUserId(t, mobile, userId);
	}

}
