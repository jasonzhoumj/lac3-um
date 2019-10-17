package com.linkallcloud.um.domain.party;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;

public class MockUser implements Serializable {
	private static final long serialVersionUID = -7069441362060250482L;

	private Long id;
	private String uuid;

	private Long companyId;

	private String nickName;
	private String mobile;
	private String email;
	/** 身份证号 */
	private String idCard;
	private String sex;

	@JSONField(format = "yyyy-MM-dd")
	private Date birthday;

	/** 账号 */
	@NotBlank(message = "账号不能为空")
	private String account;
	/** 密码 */
	@JSONField(serialize = false)
	private String pass;
	@JSONField(serialize = false)
	private String salt;

	private String post;// 岗位
	private String job;// 职务
	private int sms;// 短信提醒，1：需要提醒，0：不需要提醒

	/** 原密码 */
	@JSONField(serialize = false)
	private String oldpass;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getSms() {
		return sms;
	}

	public void setSms(int sms) {
		this.sms = sms;
	}

	public String getOldpass() {
		return oldpass;
	}

	public void setOldpass(String oldpass) {
		this.oldpass = oldpass;
	}

}
