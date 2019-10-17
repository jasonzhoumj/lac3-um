package com.linkallcloud.um.face.account;

import com.linkallcloud.face.message.request.FaceRequest;

public class AccountWeChatRequest extends FaceRequest {
	private static final long serialVersionUID = 1202605397245526387L;

	private String userType;
	private String openid;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
