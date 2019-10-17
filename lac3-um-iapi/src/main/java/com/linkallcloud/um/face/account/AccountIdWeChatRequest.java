package com.linkallcloud.um.face.account;

import com.linkallcloud.face.message.request.FaceRequest;

public class AccountIdWeChatRequest extends FaceRequest {
	private static final long serialVersionUID = 2844518038880122181L;

	private Long accountId;
	private String openid;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
