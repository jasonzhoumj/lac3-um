package com.linkallcloud.um.server.manager.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.linkallcloud.busilog.annotation.Module;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.manager.BaseManager;
import com.linkallcloud.um.domain.party.KhCompany;
import com.linkallcloud.um.domain.sys.KhSystemConfig;
import com.linkallcloud.um.iapi.sys.IKhSystemConfigManager;
import com.linkallcloud.um.server.service.party.IKhCompanyService;
import com.linkallcloud.um.server.service.sys.IKhSystemConfigService;

@Service(interfaceClass = IKhSystemConfigManager.class, version = "${dubbo.service.version}")
@Component
@Module(name = "系统配置")
public class KhSystemConfigManager extends BaseManager<Long, KhSystemConfig, IKhSystemConfigService>
        implements IKhSystemConfigManager {

    @Autowired
    private IKhSystemConfigService khSystemConfigService;

    @Autowired
    private IKhCompanyService khCompanyService;

    @Override
    public KhSystemConfig fetchByCompanyId(Trace t, Long companyId) {
        KhCompany company = khCompanyService.fetchById(t, companyId);
        if (company.isTopParent()) {
            return service().fetchByCompanyId(t, companyId);
        } else {
            return service().fetchByCompanyId(t, company.rootCompanyId());
        }
    }

    @Override
    public Long save(Trace t, KhSystemConfig entity) {
        KhSystemConfig dbEntity = service().fetchByCompanyId(t, entity.getCompanyId());
        if (dbEntity != null) {
            dbEntity.setRootAreaId(entity.getRootAreaId());
            dbEntity.setEnableOrgPerm(entity.getEnableOrgPerm());
            dbEntity.setEnableAreaPerm(entity.getEnableAreaPerm());
            dbEntity.setLogo(entity.getLogo());
            service().update(t, dbEntity);
            return dbEntity.getId();
        } else {
            return service().insert(t, entity);
        }
    }

    @Override
    protected IKhSystemConfigService service() {
        return khSystemConfigService;
    }

}
