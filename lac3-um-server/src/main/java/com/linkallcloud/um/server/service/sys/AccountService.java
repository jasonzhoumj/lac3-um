package com.linkallcloud.um.server.service.sys;

import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.service.BaseService;
import com.linkallcloud.um.activity.sys.IAccountActivity;
import com.linkallcloud.um.domain.sys.Account;
import com.linkallcloud.um.exception.AuthException;
import com.linkallcloud.um.service.sys.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AccountService extends BaseService<Account, IAccountActivity> implements IAccountService {

    @Autowired
    private IAccountActivity accountActivity;

    @Override
    public IAccountActivity activity() {
        return accountActivity;
    }

    @Override
    public Account fecthByMobile(Trace t, String mobile) {
        return activity().fecthByMobile(t, mobile);
    }

    @Override
    public Account fecthByAccount(Trace t, String account) {
        return activity().fecthByAccount(t, account);
    }

    @Override
    @Transactional(readOnly = false)
    public Account loginValidate(Trace t, String account, String password) {
        return activity().loginValidate(t, account, password);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean updatePassword(Trace t, Long id, String uuid, String oldPwd, String newPwd) {
        return activity().updatePassword(t, id, uuid, oldPwd, newPwd);
    }

    @Override
    public Account fechByWechatOpenId(Trace t, String userType, String openid) {
        return activity().fechByWechatOpenId(t, userType, openid);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean updateAccountWechatOpenId(Trace t, Long accountId, String openid) {
        return activity().updateAccountWechatOpenId(t, accountId, openid);
    }

}
