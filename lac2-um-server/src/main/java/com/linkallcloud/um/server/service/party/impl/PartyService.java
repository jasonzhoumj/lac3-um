package com.linkallcloud.um.server.service.party.impl;

import org.springframework.transaction.annotation.Transactional;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.BaseRuntimeException;
import com.linkallcloud.lang.Mirror;
import com.linkallcloud.service.TreeDomainService;
import com.linkallcloud.um.domain.party.Party;
import com.linkallcloud.um.server.dao.party.IPartyDao;
import com.linkallcloud.um.server.service.party.IPartyService;

@Transactional(readOnly = true)
public abstract class PartyService<T extends Party, M extends IPartyDao<T>> extends TreeDomainService<Long, T, M>
        implements IPartyService<T> {

    @SuppressWarnings("unchecked")
    public PartyService() {
        try {
            mirror = Mirror.me((Class<T>) Mirror.getTypeParams(getClass())[0]);
        } catch (Throwable e) {
            if (log.isWarnEnabled()) {
                log.warn("!!!Fail to get TypeParams for self!", e);
            }
        }
    }

    @Override
    protected void treeBefore(Trace t, boolean isNew, T entity) throws BaseRuntimeException {
        super.treeBefore(t, isNew, entity);
        if (isNew) {
            entity.setUuid(entity.generateUuid());
            if (entity.isTopParent()) {
                entity.setParentId(0L);
                entity.setParentClass(null);
            }
        } else {
            if (entity.isTopParent()) {
                entity.setParentClass(null);
            }
        }
    }

    @Override
    public T fetchByGovCode(Trace t, String govCode) {
        return dao().fetchByGovCode(t, govCode);
    }

}
