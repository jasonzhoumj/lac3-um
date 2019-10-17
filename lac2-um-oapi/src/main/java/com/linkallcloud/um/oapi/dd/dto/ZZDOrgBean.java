package com.linkallcloud.um.oapi.dd.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 节点信息对象
 * @CLASS: ZZDOrgBean
 * @DESCRIPTION: 
 * @AUTHOR: miaoj
 * @VERSION: v1.0
 * @DATE: 2019年5月17日
 */
public class ZZDOrgBean implements Serializable {
	private static final long serialVersionUID = 4479638491578616239L;
	
	private Long orgNumber; //节点唯一标识ID
	private String name; // 节点名
	private Integer type; // 节点类型，1=地域，2=分类，3=行政区域，4=单位 5=内设机构，6=工作联络，7=虚拟节点
	private Long parentId; // 父节点ID
	private String fullName; // 节点全名
	private String orgCode; // 组织机构代码
	private String postCode; // 邮编
	private Boolean deptHiding; // 节点是否隐藏，true表示隐藏，false表示显示
	private List<String> deptPerimits; // 可以查看指定隐藏部门的其他部门列表，如果部门隐藏，则此值生效，取值为其他的部门ID组成的的字符串
	private Boolean outerDept; // 是否本部门的员工仅可见员工自己, 为true时，本部门员工默认只能看到员工自己
	private List<String> outerPermitDepts; // 本部门的员工仅可见员工自己为true时，可以配置额外可见部门，值为部门ID组成的的字符串
	private Boolean createDeptGroup; // 是否存在关联此节点的部门群，true表示是，false表示不是
	private Long orgDeptGroupOwner; // 部门群群主
	private List<String> deptManagerUserIdList; // 节点的主管列表，取值为由主管的userid组成的字符串
	private Long order; // 节点排序
	
	public Long getOrgNumber() {
		return orgNumber;
	}
	public void setOrgNumber(Long orgNumber) {
		this.orgNumber = orgNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public Boolean getDeptHiding() {
		return deptHiding;
	}
	public void setDeptHiding(Boolean deptHiding) {
		this.deptHiding = deptHiding;
	}
	public List<String> getDeptPerimits() {
		return deptPerimits;
	}
	public void setDeptPerimits(List<String> deptPerimits) {
		this.deptPerimits = deptPerimits;
	}
	public Boolean getOuterDept() {
		return outerDept;
	}
	public void setOuterDept(Boolean outerDept) {
		this.outerDept = outerDept;
	}
	public List<String> getOuterPermitDepts() {
		return outerPermitDepts;
	}
	public void setOuterPermitDepts(List<String> outerPermitDepts) {
		this.outerPermitDepts = outerPermitDepts;
	}
	public Boolean getCreateDeptGroup() {
		return createDeptGroup;
	}
	public void setCreateDeptGroup(Boolean createDeptGroup) {
		this.createDeptGroup = createDeptGroup;
	}
	public Long getOrgDeptGroupOwner() {
		return orgDeptGroupOwner;
	}
	public void setOrgDeptGroupOwner(Long orgDeptGroupOwner) {
		this.orgDeptGroupOwner = orgDeptGroupOwner;
	}
	public List<String> getDeptManagerUserIdList() {
		return deptManagerUserIdList;
	}
	public void setDeptManagerUserIdList(List<String> deptManagerUserIdList) {
		this.deptManagerUserIdList = deptManagerUserIdList;
	}
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
}