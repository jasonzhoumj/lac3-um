package com.linkallcloud.um.server.service.party.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.linkallcloud.core.busilog.annotation.ServLog;
import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.exception.BaseRuntimeException;
import com.linkallcloud.core.lang.Mirror;
import com.linkallcloud.um.domain.party.Org;
import com.linkallcloud.um.domain.party.Rel4OrgLeader;
import com.linkallcloud.um.domain.party.User;
import com.linkallcloud.um.server.dao.party.IOrgDao;
import com.linkallcloud.um.server.dao.party.IUserDao;
import com.linkallcloud.um.server.service.party.IOrgService;
import com.linkallcloud.core.util.Domains;

@Transactional(readOnly = true)
public abstract class OrgService<T extends Org, M extends IOrgDao<T>, U extends User, UM extends IUserDao<U>>
        extends PartyService<T, M> implements IOrgService<T> {

    protected Mirror<U> userMirror;

    @SuppressWarnings("unchecked")
    public OrgService() {
        super();
        try {
            userMirror = Mirror.me((Class<U>) Mirror.getTypeParams(getClass())[2]);
        } catch (Throwable e) {
            if (log.isWarnEnabled()) {
                log.warn("!!!Fail to get TypeParams for self!", e);
            }
        }
    }

    protected Class<U> getUserClass() {
        return (null == userMirror) ? null : userMirror.getType();
    }

    protected abstract UM getUserDao();

    @Transactional(readOnly = false)
    @Override
    public boolean deleteLeaders(Trace t, Long orgId, String orgUuid, Map<String, Long> userUuidIds)
            throws BaseRuntimeException {
        if (userUuidIds != null && !userUuidIds.isEmpty()) {
            List<Long> userIds = Domains.parseIds(userUuidIds);
            if (userIds != null && userIds.size() > 0) {
                int rows = dao().deleteRel4OrgLeader(t, orgId, userIds);
                return retBool(rows);
            }
        }
        return false;
    }

    @Transactional(readOnly = false)
    @Override
    @ServLog(db = true, desc = "批量给机构([(${orgId})])设置领导班子成员([(${userUuidIds})]), TID:[(${tid})]")
    public boolean addLeaders(Trace t, Long orgId, String orgUuid, Map<String, Long> userUuidIds)
            throws BaseRuntimeException {
        if (userUuidIds != null && !userUuidIds.isEmpty()) {
            List<U> dbLeaders = getUserDao().findOrgLeaders(t, orgId, orgUuid);

            List<Rel4OrgLeader> rels = new ArrayList<Rel4OrgLeader>();
            for (String uuid : userUuidIds.keySet()) {
                Long userId = userUuidIds.get(uuid);
                if (!Domains.exist(userId, dbLeaders)) {
                    rels.add(new Rel4OrgLeader(orgId, userId));
                }
            }
            if (!rels.isEmpty()) {
                int rows = dao().saveRel4OrgLeader(t, rels);
                return retBool(rows);
            }
        }
        return false;
    }

    // @Override
    // protected void before(Trace t, boolean isNew, T entity) throws
    // BaseRuntimeException {
    // super.before(t, isNew, entity);
    // if (isNew) {
    // if (entity.isTopParent()) {
    // entity.setLevel(1);
    // } else {
    // T parent = dao().fetchById(t, entity.getParentId());
    // if (parent != null) {
    // entity.setLevel(parent.getLevel() + 1);
    // }
    // }
    // } else {// 修改
    // updateCodeIfModifiedParent(t, entity);
    // }
    // }
    //
    // private void updateCodeIfModifiedParent(Trace t, T entity) {
    // boolean parentChanged = false;
    // T dbEntity = dao().fetchById(t, entity.getId());
    // if (dbEntity.isTopParent()) {
    // if (!entity.isTopParent()) {
    // parentChanged = true;
    // }
    // } else {
    // if (!dbEntity.getParentId().equals(entity.getParentId())) {
    // parentChanged = true;
    // }
    // }
    //
    // if (parentChanged) {
    // if (entity.isTopParent()) {
    // entity.setCode(Domains.genDomainCode(null, entity));
    // } else {
    // T parent = dao().fetchById(t, entity.getParentId());
    // if (parent != null) {
    // entity.setCode(Domains.genDomainCode(parent, entity));
    // } else {
    // throw new BaseRuntimeException(BaseRuntimeException.RUNTIME_EXCEPTION,
    // "parentId参数错误。");
    // }
    // }
    // updateCode(t, entity.getId(), entity.getCode());
    // }
    // }
    //
    // @Override
    // protected void after(Trace t, boolean isNew, T entity) throws
    // BaseRuntimeException {
    // super.after(t, isNew, entity);
    // if (isNew) {// 新增
    // if (entity.isTopParent()) {
    // entity.setCode(Domains.genDomainCode(null, entity));
    // } else {
    // T parent = dao().fetchById(t, entity.getParentId());
    // if (parent != null) {
    // entity.setCode(Domains.genDomainCode(parent, entity));
    // } else {
    // throw new BaseRuntimeException(BaseRuntimeException.RUNTIME_EXCEPTION,
    // "parentId参数错误。");
    // }
    // }
    // updateCode(t, entity.getId(), entity.getCode());
    // }
    // }

}
