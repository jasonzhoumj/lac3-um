package com.linkallcloud.um.server.service.party;

import java.util.List;
import java.util.Map;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.BaseRuntimeException;
import com.linkallcloud.pagination.Page;
import com.linkallcloud.um.domain.party.Role;
import com.linkallcloud.um.domain.party.User;
import com.linkallcloud.um.domain.sys.Menu;

public abstract interface IRoleService<T extends Role, U extends User> extends IPartyService<T> {

	/**
	 * 根据companyId，获取companyId所作公司的角色page
	 * 
	 * @param tid
	 * @param companyId
	 * @param type      type=Domains.ROLE_NORMAL:本公司普通角色；type=Domains.ROLE_SYSTEM:本公司系统角色；其它：本公司全部角色
	 * @param page
	 * @return
	 */
	Page<Long, T> findCompanyRolePage(Trace t, Long companyId, int type, Page<Long, T> page)
			throws BaseRuntimeException;

	/**
	 * 根据companyId，获取companyId所作公司的角色
	 * 
	 * @param tid
	 * @param companyId
	 * @param type      type=Domains.ROLE_NORMAL:本公司普通角色；type=Domains.ROLE_SYSTEM:本公司系统角色；其它：本公司全部角色
	 * @return
	 */
	List<T> findCompanyRoles(Trace t, Long companyId, int type) throws BaseRuntimeException;

	/**
	 * 根据companyId，获取companyId所在公司的所有角色page，包含系统角色
	 * 
	 * @param t
	 * @param companyId
	 * @param page
	 * @return
	 * @throws BaseRuntimeException
	 */
	Page<Long, T> findCompanyAllRolePage(Trace t, Long companyId, Page<Long, T> page) throws BaseRuntimeException;

	/**
	 * 根据companyId，获取companyId所在公司的所有角色，包含系统角色
	 * 
	 * @param t
	 * @param companyId
	 * @return
	 * @throws BaseRuntimeException
	 */
	List<T> findCompanyAllRoles(Trace t, Long companyId) throws BaseRuntimeException;

	/**
	 * 根据orgId和levelNotEqual，获取orgId所在公司的所有角色，包含level符合的系统角色
	 * 
	 * @param t
	 * @param companyId
	 * @param roleLevel roleLevel=9:所有角色，roleLevel!=9:增加条件level<>9
	 * @return
	 */
	List<T> findCompanyRolesByLevel(Trace t, Long companyId, Integer roleLevel);

	/**
	 * 获取用户拥有的角色分页列表
	 * 
	 * @param tid
	 * @param page
	 * @return
	 */
	Page<Long, T> findPage4User(Trace t, Page<Long, T> page);

	/**
	 * 获取用户未拥有的角色分页列表
	 * 
	 * @param tid
	 * @param page
	 * @return
	 */
	Page<Long, T> findNoRolePage4User(Trace t, Page<Long, T> page);

	/**
	 * 获取用户的角色列表
	 * 
	 * @param tid
	 * @param userId
	 * @return
	 */
	List<T> find4User(Trace t, Long userId);

	/**
	 * 给角色分配人员
	 * 
	 * @param tid
	 * @param roleId
	 * @param roleUuid
	 * @param userUuidIds
	 * @return
	 */
	boolean addRoleUsers(Trace t, Long roleId, String roleUuid, Map<String, Long> userUuidIds);

	/**
	 * 删除角色已分配的人员
	 * 
	 * @param tid
	 * @param roleId
	 * @param roleUuid
	 * @param userUuidIds
	 * @return
	 */
	boolean removeRoleUsers(Trace t, Long roleId, String roleUuid, Map<String, Long> userUuidIds);

	boolean clearRoleUsers(Trace t, Long roleId, String roleUuid);

	List<U> getRoleUsers(Trace t, Long roleId, String roleUuid);

	/**
	 * 增加角色许可访问的应用
	 * 
	 * @param tid
	 * @param roleId
	 * @param roleUuid
	 * @param appUuidIds
	 * @return
	 */
	boolean addRoleApps(Trace t, Long roleId, String roleUuid, Map<String, Long> appUuidIds);

	/**
	 * 删除角色许可访问的应用
	 * 
	 * @param tid
	 * @param roleId
	 * @param roleUuid
	 * @param appUuidIds
	 * @return
	 */
	boolean removeRoleApps(Trace t, Long roleId, String roleUuid, Map<String, Long> appUuidIds);

	/**
	 * 保存某角色某应用的菜单权限
	 * 
	 * @param tid
	 * @param roleId
	 * @param roleUuid
	 * @param appId
	 * @param appUuid
	 * @param menuUuidIds
	 * @return
	 */
	Boolean saveRoleAppMenuPerm(Trace t, Long roleId, String roleUuid, Long appId, String appUuid,
			Map<String, Long> menuUuidIds);

	Boolean saveRoleAppOrgPerm(Trace t, Long roleId, String roleUuid, Long appId, String appUuid,
			Map<String, Long> menuUuidIds);

	Boolean saveRoleAppAreaPerm(Trace t, Long roleId, String roleUuid, Long appId, String appUuid,
			Map<String, Long> areaUuidIds);

	/**
	 * 获取某角色某应用有权限的菜单id数组
	 * 
	 * @param tid
	 * @param roleId
	 * @param appId
	 * @return
	 */
	Long[] findPermedMenuIds(Trace t, Long roleId, Long appId);

	Long[] findPermedOrgIds(Trace t, Long roleId, Long appId);

	Long[] findPermedAreaIds(Trace t, Long roleId, Long appId);

	/**
	 * 获取某角色某应用有权限的菜单ResCode数组
	 * 
	 * @param tid
	 * @param roleId
	 * @param appId
	 * @return
	 */
	String[] findPermedMenuResCodes(Trace t, Long roleId, Long appId);

	List<Menu> findPermedMenus(Trace t, Long roleId, Long appId);

}