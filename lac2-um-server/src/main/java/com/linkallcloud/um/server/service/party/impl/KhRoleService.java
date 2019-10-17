package com.linkallcloud.um.server.service.party.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.um.domain.party.KhCompany;
import com.linkallcloud.um.domain.party.KhRole;
import com.linkallcloud.um.domain.party.KhUser;
import com.linkallcloud.um.domain.sys.Menu;
import com.linkallcloud.um.server.dao.party.IKhCompanyDao;
import com.linkallcloud.um.server.dao.party.IKhRoleDao;
import com.linkallcloud.um.server.dao.party.IKhUserDao;
import com.linkallcloud.um.server.dao.sys.IApplicationDao;
import com.linkallcloud.um.server.service.party.IKhRoleService;

@Service
public class KhRoleService extends RoleService<KhRole, KhUser, KhCompany, IKhRoleDao, IKhUserDao, IKhCompanyDao>
        implements IKhRoleService {

    @Autowired
    private IKhUserDao khUserDao;

    @Autowired
    private IKhCompanyDao khCompanyDao;

    @Autowired
    private IKhRoleDao khRoleDao;

    @Autowired
    private IApplicationDao applicationDao;

    @Override
    public IKhUserDao userDao() {
        return khUserDao;
    }

    @Override
    public IKhCompanyDao orgDao() {
        return khCompanyDao;
    }

    @Override
    public IApplicationDao applicationDao() {
        return applicationDao;
    }

    @Override
    public IKhRoleDao dao() {
        return khRoleDao;
    }

    @Override
    public List<Menu> findPermedMenus(Trace t, Long roleId, Long appId) {
        return dao().findPermedMenus(t, roleId, appId);
    }

}
