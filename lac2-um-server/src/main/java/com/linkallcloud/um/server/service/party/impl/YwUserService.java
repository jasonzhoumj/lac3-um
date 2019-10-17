package com.linkallcloud.um.server.service.party.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.BaseRuntimeException;
import com.linkallcloud.pagination.Page;
import com.linkallcloud.query.Query;
import com.linkallcloud.query.rule.Equal;
import com.linkallcloud.um.domain.party.YwCompany;
import com.linkallcloud.um.domain.party.YwDepartment;
import com.linkallcloud.um.domain.party.YwRole;
import com.linkallcloud.um.domain.party.YwUser;
import com.linkallcloud.um.server.dao.party.IYwCompanyDao;
import com.linkallcloud.um.server.dao.party.IYwDepartmentDao;
import com.linkallcloud.um.server.dao.party.IYwRoleDao;
import com.linkallcloud.um.server.dao.party.IYwUserDao;
import com.linkallcloud.um.server.service.party.IYwUserService;

@Service
@Transactional(readOnly = true)
public class YwUserService extends
		UserService<YwUser, IYwUserDao, YwDepartment, IYwDepartmentDao, YwCompany, IYwCompanyDao, YwRole, IYwRoleDao>
		implements IYwUserService {

	@Autowired
	private IYwUserDao ywUserDao;

	@Autowired
	private IYwDepartmentDao ywDepartmentDao;

	@Autowired
	private IYwCompanyDao ywCompanyDao;

	@Autowired
	private IYwRoleDao ywRoleDao;

//	@Autowired
//	private IAreaDao areaDao;

	@Override
	public IYwUserDao dao() {
		return ywUserDao;
	}

	@Override
	protected IYwDepartmentDao getDepartmentDao() {
		return ywDepartmentDao;
	}

	@Override
	protected IYwCompanyDao getCompanyDao() {
		return ywCompanyDao;
	}

	@Override
	protected IYwRoleDao getRoleDao() {
		return ywRoleDao;
	}

	// @CacheEvict(value = "YwUser", key = "\"ID-\" + #entity.id")
	@Transactional(readOnly = false)
	@Override
	public boolean update(Trace t, YwUser entity) throws BaseRuntimeException {
		return super.update(t, entity);
	}

	// @CacheEvict(value = "YwUser", key = "\"ID-\" + #id")
	@Transactional(readOnly = false)
	@Override
	public boolean delete(Trace t, Long id, String uuid) throws BaseRuntimeException {
		return super.delete(t, id, uuid);
	}

	// @Cacheable(value = "YwUser", key = "\"ID-\" + #id")
	@Transactional(readOnly = false)
	@Override
	public YwUser fetchById(Trace t, Long id) {
		return super.fetchById(t, id);
	}

	// @Cacheable(value = "YwUser", key = "\"ID-\" + #id")
	@Transactional(readOnly = false)
	@Override
	public YwUser fetchByIdUuid(Trace t, Long id, String uuid) {
		return super.fetchByIdUuid(t, id, uuid);
	}

	@Override
	public Page<Long, YwUser> findPermedUserPage(Trace t, Page<Long, YwUser> page) {
		page.checkPageParameters();
		try {
			PageHelper.startPage(page.getPageNum(), page.getLength());
			List<YwUser> list = dao().findPermedUserPage(t, page);
			if (list instanceof com.github.pagehelper.Page) {
				page.setRecordsTotal(((com.github.pagehelper.Page<YwUser>) list).getTotal());
				page.checkPageParameters();
				page.setRecordsFiltered(page.getRecordsTotal());
				page.addDataAll(list);
			}
			return page;
		} finally {
			PageHelper.clearPage();
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void cleanOtherUserMobileByUserId(Trace t, String mobile, Long userId) {
		Query query = new Query();
		query.addRule(new Equal("mobileEq", mobile));
		List<YwUser> users = find(t, query);
		if (users != null && users.size() > 0) {
			for (YwUser user : users) {
				if (!user.getId().equals(userId)) {
					user.setMobile("");
					user.setPassword(null);
					user.setSalt(null);
					dao().update(t, user);
				}
			}
		}
	}

	@Override
	public YwUser findByMobileAndDdStatus(Trace t, String mobile) {
		return dao().findByMobileAndDdStatus(t, mobile);
	}

}
