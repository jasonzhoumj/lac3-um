package com.linkallcloud.um.server.service.party;

import com.linkallcloud.um.activity.party.IYwCompanyActivity;
import com.linkallcloud.um.activity.party.IYwDepartmentActivity;
import com.linkallcloud.um.activity.party.IYwUserActivity;
import com.linkallcloud.um.domain.party.YwCompany;
import com.linkallcloud.um.domain.party.YwDepartment;
import com.linkallcloud.um.domain.party.YwUser;
import com.linkallcloud.um.service.party.IYwDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class YwDepartmentService
        extends DepartmentService<YwDepartment, IYwDepartmentActivity, YwUser, IYwUserActivity, YwCompany, IYwCompanyActivity>
        implements IYwDepartmentService {

    @Autowired
    private IYwDepartmentActivity ywDepartmentActivity;

    @Autowired
    private IYwUserActivity ywUserActivity;

    @Autowired
    private IYwCompanyActivity ywCompanyActivity;

    @Override
    protected IYwUserActivity getUserActivity() {
        return ywUserActivity;
    }

    @Override
    public IYwDepartmentActivity activity() {
        return ywDepartmentActivity;
    }

    @Override
    protected IYwCompanyActivity getCompanyActivity() {
        return ywCompanyActivity;
    }

}
