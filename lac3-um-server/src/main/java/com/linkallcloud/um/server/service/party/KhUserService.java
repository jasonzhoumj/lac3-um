package com.linkallcloud.um.server.service.party;

import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.pagination.Page;
import com.linkallcloud.um.activity.party.*;
import com.linkallcloud.um.activity.sys.IAreaActivity;
import com.linkallcloud.um.domain.party.KhCompany;
import com.linkallcloud.um.domain.party.KhDepartment;
import com.linkallcloud.um.domain.party.KhRole;
import com.linkallcloud.um.domain.party.KhUser;
import com.linkallcloud.um.service.party.IKhUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class KhUserService extends
        UserService<KhUser, IKhUserActivity, KhDepartment, IKhDepartmentActivity, KhCompany, IKhCompanyActivity, KhRole, IKhRoleActivity>
        implements IKhUserService {

    @Autowired
    private IKhUserActivity khUserActivity;

    @Autowired
    private IKhDepartmentActivity khDepartmentActivity;

    @Autowired
    private IKhCompanyActivity khCompanyActivity;

    @Autowired
    private IKhRoleActivity khRoleActivity;

    @Autowired
    private IYwCompanyActivity ywCompanyActivity;

    @Autowired
    private IYwUserActivity ywUserActivity;

    @Autowired
    private IAreaActivity areaActivity;

    @Override
    public IKhUserActivity activity() {
        return khUserActivity;
    }

    @Override
    protected IKhDepartmentActivity getDepartmentActivity() {
        return khDepartmentActivity;
    }

    @Override
    protected IKhCompanyActivity getCompanyActivity() {
        return khCompanyActivity;
    }

    @Override
    protected IKhRoleActivity getRoleActivity() {
        return khRoleActivity;
    }


    @Override
    public Page<KhUser> findSelfUserPage(Trace t, Page<KhUser> page) {
        return activity().findSelfUserPage(t, page);
    }

    @Override
    public Page<KhUser> findPermedSelfUserPage(Trace t, Page<KhUser> page) {
        return activity().findPermedSelfUserPage(t, page);
    }

}
