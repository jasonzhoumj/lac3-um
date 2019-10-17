package com.linkallcloud.um.server.service.sys.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.BaseRuntimeException;
import com.linkallcloud.lang.Strings;
import com.linkallcloud.security.Securities;
import com.linkallcloud.service.BaseService;
import com.linkallcloud.um.domain.party.KhUser;
import com.linkallcloud.um.domain.party.YwUser;
import com.linkallcloud.um.domain.sys.Account;
import com.linkallcloud.um.server.dao.party.IKhUserDao;
import com.linkallcloud.um.server.dao.party.IYwUserDao;
import com.linkallcloud.um.server.dao.sys.IAccountDao;
import com.linkallcloud.um.server.service.sys.IAccountService;

@Service
@Transactional(readOnly = true)
public class AccountService extends BaseService<Long, Account, IAccountDao> implements IAccountService {

    @Autowired
    private IAccountDao accountDao;

    @Autowired
    private IYwUserDao ywUserDao;
    @Autowired
    private IKhUserDao kuUserDao;

    @Override
    public IAccountDao dao() {
        return accountDao;
    }

    @Override
    public Account fecthByMobile(Trace t, String mobile) {
        return dao().fecthByMobile(t, mobile);
    }

    @Override
    public Account fecthByAccount(Trace t, String account) {
        return dao().fecthByAccount(t, account);
    }

    @Override
    @Transactional(readOnly = false)
    public Account loginValidate(Trace t, String account, String password) throws BaseRuntimeException {
        if (Strings.isBlank(account)) {
            throw new BaseRuntimeException("10002000", "登录名或者密码错误，请重新输入！");
        }
        if (Strings.isBlank(password)) {
            throw new BaseRuntimeException("10002001", "登录名或者密码错误，请重新输入！");
        }

        Account dbAccount = dao().fecthByAccount(t, account);
        if (dbAccount == null) {
            throw new BaseRuntimeException("10002000", "登录名或者密码错误，请重新输入！");
        }
        if (dbAccount.getStatus() != 0) {
            throw new BaseRuntimeException("10002", "您的账号被限制登陆，请联系管理员！");
        }

        // setClientInfo2Cache(lvo.getLoginName(), lvo.getClient());

        if (Securities.validePassword4Md5Src(password, dbAccount.getSalt(), dbAccount.getPassword())) {
            dao().updateLastLoginTime(t, dbAccount.getId());
            return dbAccount;
        }
        throw new BaseRuntimeException("10002000", "登录名或者密码错误，请重新输入！");
    }

    @Transactional(readOnly = false)
    @Override
    public boolean updatePassword(Trace t, Long id, String uuid, String oldPwd, String newPwd)
            throws BaseRuntimeException {
        Account account = this.fetchByIdUuid(t, id, uuid);
        if (account != null) {
            if (Securities.validePassword4Md5Src(oldPwd, account.getSalt(), account.getPassword())) {
                account.setSalt(account.generateUuid());
                account.setPassword(Securities.password4Md5Src(newPwd, account.getSalt()));
                int rows = dao().update(t, account);
                if (rows > 0) {
                    log.debug("update密码 成功，tid：" + t.getTid() + ", id:" + account.getId());
                } else {
                    log.error("update密码 失败，tid：" + t.getTid() + ", id:" + account.getId());
                }
                return retBool(rows);
            }
        }
        return false;
    }

    @Override
    public Account fechByWechatOpenId(Trace t, String userType, String openid) {
        Account account = dao().fechByWechatOpenId(t, userType, openid);
        if (account != null) {
            if (YwUser.class.getSimpleName().equals(account.getUserType())) {
                YwUser u = ywUserDao.fecthByAccount(t, account.getAccount());
                if (u.getStatus() != 0) {
                    throw new BaseRuntimeException("10002", "您的账号暂时无法登陆系统，请联系管理员！");
                }
            } else if (KhUser.class.getSimpleName().equals(account.getUserType())) {
                KhUser u = kuUserDao.fecthByAccount(t, account.getAccount());
                if (u.getStatus() != 0) {
                    throw new BaseRuntimeException("10002", "您的账号暂时无法登陆系统，请联系管理员！");
                }
            }
        }
        return account;
    }

    @Transactional(readOnly = false)
    @Override
    public boolean updateAccountWechatOpenId(Trace t, Long accountId, String openid) {
        int rows = dao().updateAccountWechatOpenId(t, accountId, openid);
        return retBool(rows);
    }

}
