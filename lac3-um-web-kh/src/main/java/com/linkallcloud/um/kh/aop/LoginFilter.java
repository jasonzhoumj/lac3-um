package com.linkallcloud.um.kh.aop;

import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.principal.AccountMapping;
import com.linkallcloud.core.principal.Principal;
import com.linkallcloud.um.iapi.party.IKhUserManager;
import com.linkallcloud.um.iapi.party.IYwUserManager;
import com.linkallcloud.web.filter.AbstractPrincipalFilter;
import com.linkallcloud.web.session.SessionUser;

import java.util.List;

public class LoginFilter extends AbstractPrincipalFilter {

	private String myAppCode;
	private IKhUserManager khUserManager;

	private String loginUrl;

	public LoginFilter() {
		super();
	}

	public LoginFilter(String myAppCode, IKhUserManager khUserManager, String loginUrl) {
		super();
		this.myAppCode = myAppCode;
		this.khUserManager = khUserManager;
		this.loginUrl = loginUrl;
	}

	public LoginFilter(List<String> ignoreRes, boolean override) {
		super(ignoreRes, override);
	}

	@Override
	protected String getAppCode() {
		return myAppCode;
	}

	@Override
	protected String getLoginUrl() {
		return loginUrl;
	}

	@Override
	protected SessionUser getSessionUserByPrincipal(Principal ssoPrincipal) {
		if (ssoPrincipal != null) {
			String loginName = getLoginName(ssoPrincipal);
			return getUserByLoginName(loginName);
		}
		return null;
	}

	private SessionUser getUserByLoginName(String loginName) {
		return khUserManager.assembleSessionUser(new Trace(true), loginName, myAppCode);
	}

	private String getLoginName(Principal ssoPrincipal) {
		if (ssoPrincipal.getMappingType() == AccountMapping.Mapping.getCode().intValue()
				&& !com.linkallcloud.core.lang.Strings.isBlank(ssoPrincipal.getSiteId())) {
			return ssoPrincipal.getSiteId();
		} else {
			return ssoPrincipal.getSiteId();
		}
	}

}
