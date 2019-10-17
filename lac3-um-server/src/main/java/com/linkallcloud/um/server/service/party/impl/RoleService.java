package com.linkallcloud.um.server.service.party.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.linkallcloud.busilog.annotation.Module;
import com.linkallcloud.busilog.annotation.ServLog;
import com.linkallcloud.castor.Castors;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.BaseRuntimeException;
import com.linkallcloud.exception.Exceptions;
import com.linkallcloud.lang.Mirror;
import com.linkallcloud.lang.Strings;
import com.linkallcloud.pagination.Page;
import com.linkallcloud.query.Query;
import com.linkallcloud.query.rule.CompareRule;
import com.linkallcloud.query.rule.Equal;
import com.linkallcloud.um.domain.party.Org;
import com.linkallcloud.um.domain.party.Role;
import com.linkallcloud.um.domain.party.User;
import com.linkallcloud.um.domain.sys.Application;
import com.linkallcloud.um.server.dao.party.IOrgDao;
import com.linkallcloud.um.server.dao.party.IRoleDao;
import com.linkallcloud.um.server.dao.party.IUserDao;
import com.linkallcloud.um.server.dao.sys.IApplicationDao;
import com.linkallcloud.um.server.service.party.IRoleService;
import com.linkallcloud.util.Domains;

@Module(name = "角色")
@Transactional(readOnly = true)
public abstract class RoleService<R extends Role, U extends User, O extends Org, RD extends IRoleDao<R, U>, UD extends IUserDao<U>, OD extends IOrgDao<O>>
        extends PartyService<R, RD> implements IRoleService<R, U> {

    protected Mirror<R> roleMirror;
    protected Mirror<U> userMirror;
    protected Mirror<O> orgMirror;

    @SuppressWarnings("unchecked")
    public RoleService() {
        super();
        try {
            roleMirror = Mirror.me((Class<R>) Mirror.getTypeParams(getClass())[0]);
            userMirror = Mirror.me((Class<U>) Mirror.getTypeParams(getClass())[1]);
            orgMirror = Mirror.me((Class<O>) Mirror.getTypeParams(getClass())[2]);
        } catch (Throwable e) {
            if (log.isWarnEnabled()) {
                log.warn("!!!Fail to get TypeParams for self!", e);
            }
        }
    }

    public Class<R> getDomainClass() {
        return (null == roleMirror) ? null : roleMirror.getType();
    }

    public Class<U> getUserClass() {
        return (null == userMirror) ? null : userMirror.getType();
    }

    public Class<O> getOrgClass() {
        return (null == orgMirror) ? null : orgMirror.getType();
    }

    public abstract UD userDao();

    public abstract OD orgDao();

    public abstract IApplicationDao applicationDao();

    @Override
    public List<R> find4User(Trace t, Long userId) {
        return dao().find4User(t, userId);
    }

    @Override
    public Page<Long, R> findCompanyRolePage(Trace t, Long companyId, int type, Page<Long, R> page)
            throws BaseRuntimeException {
        if (companyId == null) {
            throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER, "companyId参数错误！");
        }
        if (type == Domains.ROLE_NORMAL) {
            page.addRule(new Equal("type", Domains.ROLE_NORMAL));
            page.addRule(new Equal("companyId", companyId));
            return this.findPage(t, page);
        } else if (type == Domains.ROLE_SYSTEM) {
            page.addRule(new Equal("type", Domains.ROLE_SYSTEM));
            return this.findPage(t, page);
        } else {
            return this.findCompanyAllRolePage(t, companyId, page);
        }
    }

    @Override
    public List<R> findCompanyRoles(Trace t, Long companyId, int type) throws BaseRuntimeException {
        if (companyId == null) {
            throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER, "companyId参数错误！");
        }

        Query query = new Query();
        if (type == Domains.ROLE_NORMAL) {
            query.addRule(new Equal("type", Domains.ROLE_NORMAL));
            query.addRule(new Equal("companyId", companyId));
            return this.find(t, query);
        } else if (type == Domains.ROLE_SYSTEM) {
            query.addRule(new Equal("type", Domains.ROLE_SYSTEM));
            return this.find(t, query);
        } else {
            return this.findCompanyAllRoles(t, companyId);
        }
    }

    @Override
    public List<R> findCompanyAllRoles(Trace t, Long companyId) throws BaseRuntimeException {
        if (companyId == null) {
            throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER, "companyId参数错误！");
        }
        return dao().findCompanyAllRole(t, companyId);
    }

    @Override
	public List<R> findCompanyRolesByLevel(Trace t, Long companyId, Integer roleLevel) {
    	if (companyId == null) {
            throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER, "companyId参数错误！");
        }
        return dao().findCompanyRolesByLevel(t, companyId, roleLevel);
	}

	@Override
    public Page<Long, R> findCompanyAllRolePage(Trace t, Long companyId, Page<Long, R> page)
            throws BaseRuntimeException {
        if (companyId == null) {
            throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER, "companyId参数错误！");
        }

        page.checkPageParameters();
        try {
            PageHelper.startPage(page.getPageNum(), page.getLength());
            List<R> list = dao().findCompanyAllRole(t, companyId);
            if (list instanceof com.github.pagehelper.Page) {
                page.setRecordsTotal(((com.github.pagehelper.Page<R>) list).getTotal());
                page.checkPageParameters();
                page.setRecordsFiltered(page.getRecordsTotal());
                page.addDataAll(list);
            }
            return page;
        } finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public Page<Long, R> findNoRolePage4User(Trace t, Page<Long, R> page) {
        page.checkPageParameters();

        if (!page.hasRule4Field("userId")) {
            return page;
        }

        CompareRule cr = (CompareRule) page.getRule4Field("userId");
        Long userId = Castors.me().castTo(cr.getValue(), Long.class);
        U user = userDao().fetchById(t, userId);
        page.addRule(new Equal("parentId", user.myCompanyId()));

        try {
            PageHelper.startPage(page.getPageNum(), page.getLength());
            List<R> list = dao().findNoRolePage4User(t, page);
            if (list instanceof com.github.pagehelper.Page) {
                page.setRecordsTotal(((com.github.pagehelper.Page<R>) list).getTotal());
                page.checkPageParameters();
                page.setRecordsFiltered(page.getRecordsTotal());
                page.addDataAll(list);
            }
            return page;
        } finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public Page<Long, R> findPage4User(Trace t, Page<Long, R> page) {
        page.checkPageParameters();

        if (!page.hasRule4Field("userId")) {
            return page;
        }

        // Long userId = Castors.me().castTo(query.get("userId"), Long.class);
        // U user = userDao().fetchById(t, userId);
        // query.put("parentId", user.myCompanyId());

        try {
            PageHelper.startPage(page.getPageNum(), page.getLength());
            List<R> list = dao().findPage4User(t, page);
            if (list instanceof com.github.pagehelper.Page) {
                page.setRecordsTotal(((com.github.pagehelper.Page<R>) list).getTotal());
                page.checkPageParameters();
                page.setRecordsFiltered(page.getRecordsTotal());
                page.addDataAll(list);
            }
            return page;
        } finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public List<U> getRoleUsers(Trace t, Long roleId, String roleUuid) {
        R role = fetchByIdUuid(t, roleId, roleUuid);
        if (role != null) {
            return dao().getRoleUsers(t, roleId);
        }
        return null;
    }

    public List<U> findUsersUuidIds(Trace t, Map<String, Long> userUuidIdMap) {
        List<Long> ids = Domains.parseIds(userUuidIdMap);
        if (ids != null && ids.size() > 0) {
            List<U> entities = userDao().findByIds(t, ids);
            if (entities != null && !entities.isEmpty()) {
                List<U> results = new ArrayList<U>();
                for (U entity : entities) {
                    if (entity.getUuid() != null && entity.getId().equals(userUuidIdMap.get(entity.getUuid()))) {
                        results.add(entity);
                    }
                }
                return results;
            }
        }
        return null;
    }

    @Transactional(readOnly = false)
    @Override
    @ServLog(db = true, desc = "为角色([(${roleId})]) 添加关联用户([(${userUuidIds})]), TID:[(${t.tid})]")
    public boolean addRoleUsers(Trace t, Long roleId, String roleUuid, Map<String, Long> userUuidIds) {
        R role = fetchByIdUuid(t, roleId, roleUuid);
        if (role != null) {
            List<U> checkedEntities = findUsersUuidIds(t, userUuidIds);
            if (checkedEntities != null && !checkedEntities.isEmpty() && checkedEntities.size() == userUuidIds.size()) {
                List<Long> userIds = Domains.parseIds(userUuidIds);
                return dao().addRoleUsers(t, roleId, userIds);
            }
        }
        return false;
    }

    @Transactional(readOnly = false)
    @Override
    @ServLog(db = true, desc = "为角色([(${roleId})]) 移除关联用户([(${userUuidIds})]), TID:[(${t.tid})]")
    public boolean removeRoleUsers(Trace t, Long roleId, String roleUuid, Map<String, Long> userUuidIds) {
        R role = fetchByIdUuid(t, roleId, roleUuid);
        if (role != null) {
            List<U> checkedEntities = findUsersUuidIds(t, userUuidIds);
            if (checkedEntities != null && !checkedEntities.isEmpty() && checkedEntities.size() == userUuidIds.size()) {
                List<Long> userIds = Domains.parseIds(userUuidIds);
                return dao().removeRoleUsers(t, roleId, userIds);
            }
        }
        return false;
    }

    public List<Application> findAppsByUuidIds(Trace t, Map<String, Long> appUuidIds) {
        List<Long> ids = Domains.parseIds(appUuidIds);
        if (ids != null && ids.size() > 0) {
            List<Application> entities = applicationDao().findByIds(t, ids);
            if (entities != null && !entities.isEmpty()) {
                List<Application> results = new ArrayList<Application>();
                for (Application entity : entities) {
                    if (entity.getUuid() != null && entity.getId().equals(appUuidIds.get(entity.getUuid()))) {
                        results.add(entity);
                    }
                }
                return results;
            }
        }
        return null;
    }

    @Transactional(readOnly = false)
    @Override
    @ServLog(db = true, desc = "为角色([(${roleId})]) 添加许可应用([(${appUuidIds})]), TID:[(${t.tid})]")
    public boolean addRoleApps(Trace t, Long roleId, String roleUuid, Map<String, Long> appUuidIds) {
        R role = fetchByIdUuid(t, roleId, roleUuid);
        if (role != null) {
            List<Application> checkedEntities = findAppsByUuidIds(t, appUuidIds);
            if (checkedEntities != null && !checkedEntities.isEmpty() && checkedEntities.size() == appUuidIds.size()) {
                List<Long> appIds = Domains.parseIds(appUuidIds);
                return dao().addRoleApps(t, roleId, appIds);
            }
        }
        return false;
    }

    @Transactional(readOnly = false)
    @Override
    @ServLog(db = true, desc = "为角色([(${roleId})]) 移除许可应用([(${appUuidIds})]), TID:[(${t.tid})]")
    public boolean removeRoleApps(Trace t, Long roleId, String roleUuid, Map<String, Long> appUuidIds) {
        R role = fetchByIdUuid(t, roleId, roleUuid);
        if (role != null) {
            List<Application> checkedEntities = findAppsByUuidIds(t, appUuidIds);
            if (checkedEntities != null && !checkedEntities.isEmpty() && checkedEntities.size() == appUuidIds.size()) {
                List<Long> userIds = Domains.parseIds(appUuidIds);
                return dao().removeRoleApps(t, roleId, userIds);
            }
        }
        return false;
    }

    @Transactional(readOnly = false)
    @Override
    @ServLog(db = true, desc = "为角色([(${roleId})])保存应用([(${appId})])的菜单权限([(${menuUuidIds})]), TID:[(${t.tid})]")
    public Boolean saveRoleAppMenuPerm(Trace t, Long roleId, String roleUuid, Long appId, String appUuid,
            Map<String, Long> menuUuidIds) {
        R role = fetchByIdUuid(t, roleId, roleUuid);
        Application app = applicationDao().fetchByIdUuid(t, appId, appUuid);
        if (role != null && app != null) {
            dao().clearRoleAppMenuPerms(t, roleId, appId);
            if (menuUuidIds != null && !menuUuidIds.isEmpty()) {
                List<Long> menuIds = Domains.parseIds(menuUuidIds);
                dao().saveRoleAppMenuPerms(t, roleId, appId, menuIds);
            }
            return true;
        }
        return false;
    }

    @Transactional(readOnly = false)
    @Override
    @ServLog(db = true, desc = "为角色([(${roleId})])保存应用([(${appId})])的菜单权限([(${menuUuidIds})]), TID:[(${t.tid})]")
    public Boolean saveRoleAppOrgPerm(Trace t, Long roleId, String roleUuid, Long appId, String appUuid,
            Map<String, Long> appUuidIds) {
        R role = fetchByIdUuid(t, roleId, roleUuid);
        Application app = applicationDao().fetchByIdUuid(t, appId, appUuid);
        if (role != null && app != null) {
            dao().clearRoleAppOrgPerms(t, roleId, appId);
            if (appUuidIds != null && !appUuidIds.isEmpty()) {
                List<Long> orgIds = Domains.parseIds(appUuidIds);
                dao().saveRoleAppOrgPerms(t, roleId, appId, orgIds);
            }
            return true;
        }
        return false;
    }

    @Transactional(readOnly = false)
    @Override
    public Boolean saveRoleAppAreaPerm(Trace t, Long roleId, String roleUuid, Long appId, String appUuid,
            Map<String, Long> areaUuidIds) {
        R role = fetchByIdUuid(t, roleId, roleUuid);
        Application app = applicationDao().fetchByIdUuid(t, appId, appUuid);
        if (role != null && app != null) {
            dao().clearRoleAppAreaPerms(t, roleId, appId);
            if (areaUuidIds != null && !areaUuidIds.isEmpty()) {
                List<Long> areaIds = Domains.parseIds(areaUuidIds);
                dao().saveRoleAppAreaPerms(t, roleId, appId, areaIds);
            }
            return true;
        }
        return false;
    }

    @Override
    public Long[] findPermedAreaIds(Trace t, Long roleId, Long appId) {
        return dao().findPermedAreaIds(t, roleId, appId);
    }

    @Override
    public Long[] findPermedOrgIds(Trace t, Long roleId, Long appId) {
        return dao().findPermedOrgIds(t, roleId, appId);
    }

    @Override
    public Long[] findPermedMenuIds(Trace t, Long roleId, Long appId) {
        return dao().findPermedMenuIds(t, roleId, appId);
    }

    @Override
    public String[] findPermedMenuResCodes(Trace t, Long roleId, Long appId) {
        return dao().findPermedMenuResCodes(t, roleId, appId);
    }

    @Transactional(readOnly = false)
    @Override
    @ServLog(db = true, desc = "为角色([(${roleId})]) 清空关联用户, TID:[(${tid})]")
    public boolean clearRoleUsers(Trace t, Long roleId, String roleUuid) {
        R role = fetchByIdUuid(t, roleId, roleUuid);
        if (role != null) {
            return dao().clearRoleUsers(t, roleId);
        }
        return false;
    }

    @Override
    protected void treeBefore(Trace t, boolean isNew, R entity) throws BaseRuntimeException {
        entity.setParentClass(getOrgClass().getSimpleName());
        if (exist(t, isNew, entity, "govCode", entity.getGovCode())) {
            throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER, "已经存在相同govCode的角色！");
        }
    }

    @Override
    protected void treeAfter(Trace t, boolean isNew, R entity) {
    }

    @Override
    protected boolean exist(Trace t, boolean isNew, R entity, String field, Object filedValue)
            throws BaseRuntimeException {
        if (Strings.isBlank(field) || entity == null || filedValue == null) {
            return false;
        }

        Query query = new Query();
        if (entity.getType() == Domains.ROLE_NORMAL) {
            query.addRule(new Equal("parentId", entity.getParentId()));
            // query.put("parentClass", entity.getParentClass());

            query.addRule(new Equal("type", Domains.ROLE_NORMAL));
        } else {
            query.addRule(new Equal("type", Domains.ROLE_SYSTEM));
        }

        query.addRule(new Equal(field, filedValue));
        // query.put(field, filedValue);
        // query.put(field + "_exact_match", true);// 精确匹配标志，在sql中处理
        List<R> dbEntities = this.find(t, query);

        if (isNew) {// 新增 entity.getId() == null
            if (dbEntities != null && dbEntities.size() > 0) {
                return true;
            }
        } else {// 修改
            if (dbEntities != null && dbEntities.size() > 1) {
                return true;
            } else if (dbEntities != null && dbEntities.size() == 1) {
                if (!dbEntities.get(0).getId().equals(entity.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

}
