package com.linkallcloud.um.oapi.dd.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 用户信息对象
 * @CLASS: ZZDUserBean
 * @DESCRIPTION: 
 * @AUTHOR: miaoj
 * @VERSION: v1.0
 * @DATE: 2019年5月17日
 */
public class ZZDUserBean implements Serializable {
	private static final long serialVersionUID = -5766671846878824801L;
	
	private Long userId; 		// 用户唯一标识ID
	private String userName; 	// 用户名
	private String mobile; 		// 手机号
	private String email; 		// 邮箱
	private List<Long> orgList; // 所在组织ID列表
	private String position;	//职位
	private String title;		//职务
	private String tel;			//分机号
	private String job;			//岗位
	private String workPlace;	//办公地点
	private String office;		//办公室
	private String isSenior;	//是否高管
	private String jobNumber;	//员工工号
	private String isActive;	//是否激活
	private Map<String, Long> orderInOrgs;		//部门中人员的排序
	private String virtualNet;  //虚拟网
	private Long mainOrgId;//主节点  3.31 获取人员附属信息中新增
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public List<Long> getOrgList() {
		return orgList;
	}
	public void setOrgList(List<Long> orgList) {
		this.orgList = orgList;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getIsSenior() {
		return isSenior;
	}
	public void setIsSenior(String isSenior) {
		this.isSenior = isSenior;
	}
	public String getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Map<String, Long> getOrderInOrgs() {
		return orderInOrgs;
	}
	public void setOrderInOrgs(Map<String, Long> orderInOrgs) {
		this.orderInOrgs = orderInOrgs;
	}
	public String getVirtualNet() {
		return virtualNet;
	}
	public void setVirtualNet(String virtualNet) {
		this.virtualNet = virtualNet;
	}
	public Long getMainOrgId() {
		return mainOrgId;
	}
	public void setMainOrgId(Long mainOrgId) {
		this.mainOrgId = mainOrgId;
	}
}