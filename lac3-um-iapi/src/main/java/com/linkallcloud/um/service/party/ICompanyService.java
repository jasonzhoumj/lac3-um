package com.linkallcloud.um.service.party;

import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.dto.Tree;
import com.linkallcloud.um.domain.party.Company;
import com.linkallcloud.um.dto.base.PermedAreaVo;

import java.util.List;
import java.util.Map;

public interface ICompanyService<T extends Company> extends IOrgService<T> {
	
	List<T> findSubCompanies(Trace t, Long companyId);

	Long getCompanyAreaRootId(Trace t, Long companyId, Long appId);
	Long[] getCompanyAreaRootIds(Trace t, Long companyId, Long appId);

	Long getCompanyAreaRootIdBySystemConfig(Trace t, Long companyId);

	/**
	 * 得到companyId及其以下的机构树节点，包括直接子公司节点，所有部门节点。
	 * 
	 * @param t
	 * @param companyId
	 * @return
	 */
	List<Tree> getCompanyOrgTreeList(Trace t, Long companyId);

	/**
	 * 得到companyId及其以下的机构树节点，包括所有子公司节点，所有部门节点。
	 * 
	 * @param t
	 * @param companyId
	 * @return
	 */
	List<Tree> getCompanyFullOrgTreeList(Trace t, Long companyId);

	/**
	 * 根据父公司的ID，查找直接子公司列表
	 * 
	 * @param t
	 * @param parentId
	 * @return
	 */
	List<T> findDirectCompaniesByParentId(Trace t, Long parentId);
	
	/**
	 * 根据父公司的govCode，查找所有子公司列表
	 * 
	 * @param t
	 * @param govCode
	 * @return
	 */
	List<T> findAllCompaniesByParentCode(Trace t, String govCode);

	Tree findCompanyValidMenuTree(Trace t, Long companyId, Long appId);

	List<Tree> findCompanyValidMenus(Trace t, Long companyId, Long appId);

	List<Tree> findCompanyValidOrgResource(Trace t, Long companyId);

	PermedAreaVo findCompanyValidAreaResource(Trace t, Long companyId, Long appId);

	Long[] findPermedCompanyAppAreas(Trace t, Long companyId, Long appId);

	Boolean saveCompanyAppAreaPerm(Trace t, Long companyId, String companyUuid, Long appId, String appUuid,
                                   Map<String, Long> areaUuidIds);

	Long[] findPermedCompanyAppMenus(Trace t, Long companyId, Long appId);

	Boolean saveCompanyAppMenuPerm(Trace t, Long companyId, String companyUuid, Long appId, String appUuid,
                                   Map<String, Long> menuUuidIds);

}
