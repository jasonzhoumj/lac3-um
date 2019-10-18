package com.linkallcloud.um.server.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.exception.BaseException;
import com.linkallcloud.core.exception.BaseRuntimeException;
import com.linkallcloud.core.pagination.Page;
import com.linkallcloud.core.service.BaseService;
import com.linkallcloud.um.domain.sys.Application;
import com.linkallcloud.um.server.dao.party.IYwCompanyDao;
import com.linkallcloud.um.server.dao.party.IYwUserDao;
import com.linkallcloud.um.server.dao.sys.IApplicationDao;
import com.linkallcloud.um.server.dao.sys.IAreaDao;
import com.linkallcloud.um.server.service.sys.IApplicationService;
import com.linkallcloud.um.server.utils.UmTools;

@Service
@Transactional(readOnly = true)
public class ApplicationService extends BaseService<Long, Application, IApplicationDao> implements IApplicationService {

	@Autowired
	private IApplicationDao applicationDao;
	
	@Autowired
	private IYwCompanyDao ywCompanyDao;

	@Autowired
	private IYwUserDao ywUserDao;

	@Autowired
	private IAreaDao areaDao;

	public ApplicationService() {
		super();
	}

	@Override
	public IApplicationDao dao() {
		return applicationDao;
	}

	@Override
	public Page<Long, Application> findPage4YwRole(Trace t, Page<Long, Application> page) {
		if (page == null || !page.hasRule4Field("roleId") || !page.hasRule4Field("roleUuid")) {
			throw new BaseRuntimeException("80000001", "roleId,roleUuid参数错误。");
		}

		page.checkPageParameters();

		try {
			PageHelper.startPage(page.getPageNum(), page.getLength());
			List<Application> list = dao().findPage4YwRole(t, page);
			if (list instanceof com.github.pagehelper.Page) {
				page.setRecordsTotal(((com.github.pagehelper.Page<Application>) list).getTotal());
				page.checkPageParameters();
				page.setRecordsFiltered(page.getRecordsTotal());
				page.addDataAll(list);
			}
			return page;
		} finally {
			PageHelper.clearPage();
		}
	}

	@Override
	public Application fetchByCode(Trace t, String code) {
		return dao().fetchByCode(t, code);
	}

	@Override
	public Page<Long, Application> findPage4KhRole(Trace t, Page<Long, Application> page) {
		if (page == null || !page.hasRule4Field("roleId") || !page.hasRule4Field("roleUuid")) {
			throw new BaseRuntimeException("80000001", "roleId,roleUuid参数错误。");
		}

		page.checkPageParameters();

		try {
			PageHelper.startPage(page.getPageNum(), page.getLength());
			List<Application> list = dao().findPage4KhRole(t, page);
			if (list instanceof com.github.pagehelper.Page) {
				page.setRecordsTotal(((com.github.pagehelper.Page<Application>) list).getTotal());
				page.checkPageParameters();
				page.setRecordsFiltered(page.getRecordsTotal());
				page.addDataAll(list);
			}
			return page;
		} finally {
			PageHelper.clearPage();
		}
	}

	@Override
	public Page<Long, Application> findPage4KhCompany(Trace t, Page<Long, Application> page) {
		if (page == null || !page.hasRule4Field("khCompanyId") || !page.hasRule4Field("khCompanyUuid")) {
			throw new BaseRuntimeException("80000001", "khCompanyId,khCompanyUuid参数错误。");
		}

		try {
			UmTools.addAreaCnds4YwUserAppPermission(t, page, ywCompanyDao, ywUserDao, areaDao);
		} catch (BaseException e) {
			return page;
		}

		page.checkPageParameters();
		try {
			PageHelper.startPage(page.getPageNum(), page.getLength());
			List<Application> list = dao().findPage4KhCompany(t, page);
			if (list instanceof com.github.pagehelper.Page) {
				page.setRecordsTotal(((com.github.pagehelper.Page<Application>) list).getTotal());
				page.checkPageParameters();
				page.setRecordsFiltered(page.getRecordsTotal());
				page.addDataAll(list);
			}
			return page;
		} finally {
			PageHelper.clearPage();
		}
	}

	@Override
	public List<Application> find4YwUser(Trace t, Long ywUserId) {
		return dao().find4YwUser(t, ywUserId);
	}

	@Override
	public List<Application> find4KhUser(Trace t, Long khUserId) {
		return dao().find4KhUser(t, khUserId);
	}

	@Transactional(readOnly = false)
	@Override
	public Boolean updateInterfaceInfo(Trace t, Application app) {
		int rows = dao().updateInterfaceInfo(t, app);
		return rows == 1;
	}

}
