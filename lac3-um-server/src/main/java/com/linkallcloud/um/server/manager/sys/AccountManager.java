package com.linkallcloud.um.server.manager.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.linkallcloud.busilog.annotation.Module;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.BaseRuntimeException;
import com.linkallcloud.manager.BaseManager;
import com.linkallcloud.um.domain.sys.Account;
import com.linkallcloud.um.iapi.sys.IAccountManager;
import com.linkallcloud.um.server.service.sys.IAccountService;

@Service(interfaceClass = IAccountManager.class, version = "${dubbo.service.version}")
@Component
@Module(name = "账号")
public class AccountManager extends BaseManager<Long, Account, IAccountService> implements IAccountManager {

    @Autowired
    private IAccountService accountService;

    @Override
    public Account fecthByMobile(Trace t, String mobile) {
        return service().fecthByMobile(t, mobile);
    }

    @Override
    public Account fecthByAccount(Trace t, String account) {
        return service().fecthByAccount(t, account);
    }

    @Override
    public Account loginValidate(Trace t, String accountOrMobile, String password) throws BaseRuntimeException {
        return service().loginValidate(t, accountOrMobile, password);
    }

    @Override
    public boolean updatePassword(Trace t, Long id, String uuid, String oldPwd, String newPwd)
            throws BaseRuntimeException {
        return service().updatePassword(t, id, uuid, oldPwd, newPwd);
    }

    @Override
    protected IAccountService service() {
        return accountService;
    }

    @Override
    public Account fechByWechatOpenId(Trace t, String userType, String openid) {
        return service().fechByWechatOpenId(t, userType, openid);
    }

    @Override
    public boolean updateAccountWechatOpenId(Trace t, Long accountId, String openid) {
        return service().updateAccountWechatOpenId(t, accountId, openid);
    }

    //@CachePut(value = "openid", key = "#key")
    @Override
    public String storeOpenidInCache(Trace t, String key, String openid) {
        return openid;
    }

    //@Cacheable(value = "openid", key = "#key")
    @Override
    public String fetchOpenidFromCache(Trace t, String key) {
        return null;
    }

}
