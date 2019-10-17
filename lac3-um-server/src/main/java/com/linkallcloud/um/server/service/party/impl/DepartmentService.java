package com.linkallcloud.um.server.service.party.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.BaseRuntimeException;
import com.linkallcloud.exception.Exceptions;
import com.linkallcloud.lang.Strings;
import com.linkallcloud.query.Query;
import com.linkallcloud.query.rule.Equal;
import com.linkallcloud.um.domain.party.Company;
import com.linkallcloud.um.domain.party.Department;
import com.linkallcloud.um.domain.party.User;
import com.linkallcloud.um.domain.sys.Area;
import com.linkallcloud.um.server.dao.party.ICompanyDao;
import com.linkallcloud.um.server.dao.party.IDepartmentDao;
import com.linkallcloud.um.server.dao.party.IUserDao;
import com.linkallcloud.um.server.dao.sys.IAreaDao;
import com.linkallcloud.um.server.service.party.IDepartmentService;
import com.linkallcloud.util.Domains;

@Transactional(readOnly = true)
public abstract class DepartmentService<T extends Department, M extends IDepartmentDao<T>, U extends User, UM extends IUserDao<U>, C extends Company, CM extends ICompanyDao<C>>
		extends OrgService<T, M, U, UM> implements IDepartmentService<T> {

	@Autowired
	private IAreaDao areaDao;

	protected abstract CM getCompanyDao();

	@Override
	public List<T> findCompanyDepartments(Trace t, Long companyId) {
		return dao().findCompanyDepartments(t, companyId);
	}

	@Override
	public List<T> findCompanyDirectDepartments(Trace t, Long companyId) {
		return dao().findCompanyDirectDepartments(t, companyId);
	}

	@Override
	public List<T> findDepartmentsByParentDepartmentCode(Trace t, String parentDepartmentCode) {
		return dao().findDepartmentsByParentDepartmentCode(t, parentDepartmentCode);
	}

	@Override
	public List<T> findDirectDepartmentsByParentDepartmentId(Trace t, Long parentDepartmentId) {
		return dao().findDirectDepartmentsByParentDepartmentId(t, parentDepartmentId);
	}

	@Override
	protected void before(Trace t, boolean isNew, T entity) throws BaseRuntimeException {
		super.before(t, isNew, entity);
		check(t, isNew, entity);// 检查：一个单位下只允许一个管理部门。
		dealFullName(t, isNew, entity);
		if (isNew) {
			if (entity.isTopParent()) {
				entity.setLevel(2);
			} else {
				T parent = dao().fetchById(t, entity.getParentId());
				if (parent != null) {
					entity.setLevel(parent.getLevel() + 1);
				}
			}
		} else {// 修改
			updateCodeIfModifiedParent(t, entity);
		}
	}

	private void dealFullName(Trace t, boolean isNew, T entity) {
		if (entity != null && entity.getCompanyId() != null) {
			C company = getCompanyDao().fetchById(t, entity.getCompanyId());
			if (company != null) {
				if (company.getAreaId() != null) {
					Area area = areaDao.fetchById(t, company.getAreaId());
					if (area != null) {
						entity.setFullName(area.getFullName() + entity.getName());
					}
				} else if (!Strings.isBlank(company.getGovCode())) {
					Area area = areaDao.fetchByGovCode(t, company.getGovCode());
					if (area != null) {
						entity.setFullName(area.getFullName() + entity.getName());
					}
				}
			}
		}
	}

	/**
	 * 检查：一个单位下只允许一个管理部门(type=12)。
	 * 
	 * @param t
	 * @param isNew
	 * @param entity
	 * @throws BaseRuntimeException
	 */
	protected void check(Trace t, boolean isNew, T entity) throws BaseRuntimeException {
		if (entity.getType() == 12) {
			Query query = new Query();
			query.addRule(new Equal("companyId", entity.getCompanyId()));
			query.addRule(new Equal("type", 12));

			List<T> dbEntities = find(t, query);
			if (isNew || entity.getId() == null) {
				if ((dbEntities != null) && (dbEntities.size() > 0)) {
					throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER, "机构类型错误：一个单位下只允许一个管理部门。");
				}
			} else {
				if ((dbEntities != null) && (dbEntities.size() > 1)) {
					throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER, "机构类型错误：一个单位下只允许一个管理部门。");
				}
				if ((dbEntities != null) && (dbEntities.size() == 1)
						&& (!dbEntities.get(0).getId().equals(entity.getId()))) {
					throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER, "机构类型错误：一个单位下只允许一个管理部门。");
				}
			}
		}
	}

	@Override
	protected void updateCodeIfModifiedParent(Trace t, T entity) {
		boolean parentChanged = false;
		T dbEntity = dao().fetchById(t, entity.getId());
		if (dbEntity.isTopParent()) {
			if (!entity.isTopParent()) {
				parentChanged = true;
			}
		} else {
			if (!dbEntity.getParentId().equals(entity.getParentId())) {
				parentChanged = true;
			}
		}

		if (parentChanged) {
			parseDepartmentCode(t, entity);
			updateCode(t, entity.getId(), entity.getCode());
		}
	}

	private void parseDepartmentCode(Trace t, T entity) {
		if (entity.isTopParent()) {// 直接挂在公司节点下面
			C company = getCompanyDao().fetchById(t, entity.getCompanyId());
			if (company == null) {
				throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER, "companyId参数错误。");
			}
			entity.setCode(Domains.genDomainCode(company, entity));
		} else {
			T department = dao().fetchById(t, entity.getParentId());
			if (department != null) {
				entity.setCode(Domains.genDomainCode(department, entity));
			} else {
				throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER, "parentId参数错误。");
			}
		}
	}

	@Override
	protected void after(Trace t, boolean isNew, T entity) throws BaseRuntimeException {
		super.after(t, isNew, entity);
		if (isNew) {// 新增
			parseDepartmentCode(t, entity);
			updateCode(t, entity.getId(), entity.getCode());
		}
	}

}
