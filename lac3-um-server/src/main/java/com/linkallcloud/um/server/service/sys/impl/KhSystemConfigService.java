package com.linkallcloud.um.server.service.sys.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.service.BaseService;
import com.linkallcloud.um.domain.sys.KhSystemConfig;
import com.linkallcloud.um.server.dao.sys.IKhSystemConfigDao;
import com.linkallcloud.um.server.service.sys.IKhSystemConfigService;

@Service
@Transactional(readOnly = true)
public class KhSystemConfigService extends BaseService<Long, KhSystemConfig, IKhSystemConfigDao>
        implements IKhSystemConfigService {

    @Autowired
    private IKhSystemConfigDao khSystemConfigDao;

    @Override
    public KhSystemConfig fetchByCompanyId(Trace t, Long companyId) {
        return dao().fetchByCompanyId(t, companyId);
    }

    @Override
    public IKhSystemConfigDao dao() {
        return khSystemConfigDao;
    }

}
