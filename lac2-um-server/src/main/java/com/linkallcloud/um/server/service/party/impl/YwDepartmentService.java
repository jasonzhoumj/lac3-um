package com.linkallcloud.um.server.service.party.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkallcloud.um.domain.party.YwCompany;
import com.linkallcloud.um.domain.party.YwDepartment;
import com.linkallcloud.um.domain.party.YwUser;
import com.linkallcloud.um.server.dao.party.IYwCompanyDao;
import com.linkallcloud.um.server.dao.party.IYwDepartmentDao;
import com.linkallcloud.um.server.dao.party.IYwUserDao;
import com.linkallcloud.um.server.service.party.IYwDepartmentService;

@Service
@Transactional(readOnly = true)
public class YwDepartmentService
		extends DepartmentService<YwDepartment, IYwDepartmentDao, YwUser, IYwUserDao, YwCompany, IYwCompanyDao>
		implements IYwDepartmentService {

	@Autowired
	private IYwDepartmentDao ywDepartmentDao;

	@Autowired
	private IYwUserDao ywUserDao;

	@Autowired
	private IYwCompanyDao ywCompanyDao;

	@Override
	protected IYwUserDao getUserDao() {
		return ywUserDao;
	}

	@Override
	public IYwDepartmentDao dao() {
		return ywDepartmentDao;
	}

	@Override
	protected IYwCompanyDao getCompanyDao() {
		return ywCompanyDao;
	}

}
