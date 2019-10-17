package com.linkallcloud.um.server.service.party.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkallcloud.um.domain.party.KhCompany;
import com.linkallcloud.um.domain.party.KhDepartment;
import com.linkallcloud.um.domain.party.KhUser;
import com.linkallcloud.um.server.dao.party.IKhCompanyDao;
import com.linkallcloud.um.server.dao.party.IKhDepartmentDao;
import com.linkallcloud.um.server.dao.party.IKhUserDao;
import com.linkallcloud.um.server.service.party.IKhDepartmentService;

@Service
@Transactional(readOnly = true)
public class KhDepartmentService
        extends DepartmentService<KhDepartment, IKhDepartmentDao, KhUser, IKhUserDao, KhCompany, IKhCompanyDao>
        implements IKhDepartmentService {

    @Autowired
    private IKhDepartmentDao khDepartmentDao;

    @Autowired
    private IKhUserDao khUserDao;

    @Autowired
    private IKhCompanyDao khCompanyDao;

    @Override
    protected IKhUserDao getUserDao() {
        return khUserDao;
    }

    @Override
    public IKhDepartmentDao dao() {
        return khDepartmentDao;
    }

    @Override
    protected IKhCompanyDao getCompanyDao() {
        return khCompanyDao;
    }
}
