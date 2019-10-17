package com.linkallcloud.um.server.service.party.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.um.domain.party.YwCompany;
import com.linkallcloud.um.domain.party.YwRole;
import com.linkallcloud.um.domain.party.YwUser;
import com.linkallcloud.um.domain.sys.Menu;
import com.linkallcloud.um.server.dao.party.IYwCompanyDao;
import com.linkallcloud.um.server.dao.party.IYwRoleDao;
import com.linkallcloud.um.server.dao.party.IYwUserDao;
import com.linkallcloud.um.server.dao.sys.IApplicationDao;
import com.linkallcloud.um.server.service.party.IYwRoleService;

@Service
public class YwRoleService extends RoleService<YwRole, YwUser, YwCompany, IYwRoleDao, IYwUserDao, IYwCompanyDao>
        implements IYwRoleService {

    @Autowired
    private IYwUserDao ywUserDao;

    @Autowired
    private IYwCompanyDao ywCompanyDao;

    @Autowired
    private IYwRoleDao ywRoleDao;

    @Autowired
    private IApplicationDao applicationDao;

    @Override
    public IYwUserDao userDao() {
        return ywUserDao;
    }

    @Override
    public IYwCompanyDao orgDao() {
        return ywCompanyDao;
    }

    @Override
    public IYwRoleDao dao() {
        return ywRoleDao;
    }

    @Override
    public IApplicationDao applicationDao() {
        return applicationDao;
    }

    @Override
    public List<Menu> findPermedMenus(Trace t, Long roleId, Long appId) {
        return dao().findPermedMenus(t, roleId, appId);
    }

}
