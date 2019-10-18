package com.linkallcloud.um.domain.sys;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;
import com.linkallcloud.core.domain.Domain;
import com.linkallcloud.core.domain.annotation.ShowName;

@ShowName("账号")
public class Account extends Domain<Long> {
	private static final long serialVersionUID = 1248490679564994775L;

	/**
	 * 用户类型：ZfUser/QyUser/WyUser/ZwUser
	 */
	private String userType;

	private String mobile;
	@NotBlank(message = "名称不能为空")
	private String name;

	/** 账号 */
	@NotBlank(message = "账号不能为空")
	private String account;
	/** 密码 */
	@JSONField(serialize = false)
	private String password;
	@JSONField(serialize = false)
	private String salt;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;

	public Account() {
		super();
	}

	public Account(String userType, String name, String mobile, String account, String password, String salt) {
		super();
		this.userType = userType;
		this.name = name;
		this.mobile = mobile;
		this.account = account;
		this.password = password;
		this.salt = salt;
	}

	public void desensitization() {
		this.password = null;
		this.salt = null;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}
