package com.linkallcloud.um.server.service.sys.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.service.BaseService;
import com.linkallcloud.um.domain.sys.YwSystemConfig;
import com.linkallcloud.um.server.dao.sys.IYwSystemConfigDao;
import com.linkallcloud.um.server.service.sys.IYwSystemConfigService;

@Service
@Transactional(readOnly = true)
public class YwSystemConfigService extends BaseService<Long, YwSystemConfig, IYwSystemConfigDao>
        implements IYwSystemConfigService {

    @Autowired
    private IYwSystemConfigDao ywSystemConfigDao;

    @Override
    public YwSystemConfig fetchByCompanyId(Trace t, Long companyId) {
        return dao().fetchByCompanyId(t, companyId);
    }

    @Override
    public IYwSystemConfigDao dao() {
        return ywSystemConfigDao;
    }

}
