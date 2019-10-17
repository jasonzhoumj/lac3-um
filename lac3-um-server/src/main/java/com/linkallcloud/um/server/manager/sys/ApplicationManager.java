package com.linkallcloud.um.server.manager.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.linkallcloud.busilog.annotation.Module;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.Exceptions;
import com.linkallcloud.exception.IllegalParameterException;
import com.linkallcloud.manager.BaseManager;
import com.linkallcloud.pagination.Page;
import com.linkallcloud.um.domain.sys.Application;
import com.linkallcloud.um.iapi.sys.IApplicationManager;
import com.linkallcloud.um.server.service.sys.IApplicationService;

@Service(interfaceClass = IApplicationManager.class, version = "${dubbo.service.version}")
@Component
@Module(name = "应用")
public class ApplicationManager extends BaseManager<Long, Application, IApplicationService>
        implements IApplicationManager {

    @Autowired
    private IApplicationService applicationService;

    @Override
    protected IApplicationService service() {
        return applicationService;
    }

    @Override
    public Page<Long, Application> findPage4YwRole(Trace t, Page<Long, Application> page)
            throws IllegalParameterException {
        if (page == null || !page.hasRule4Field("roleId") || !page.hasRule4Field("roleUuid")) {
            throw new IllegalParameterException(Exceptions.CODE_ERROR_PARAMETER, "roleId,roleUuid参数错误。");
        }
        return service().findPage4YwRole(t, page);
    }

    @Override
    public Application fetchByCode(Trace t, String code) {
        return service().fetchByCode(t, code);
    }

    @Override
    public Page<Long, Application> findPage4KhRole(Trace t, Page<Long, Application> page)
            throws IllegalParameterException {
        if (page == null || !page.hasRule4Field("roleId") || !page.hasRule4Field("roleUuid")) {
            throw new IllegalParameterException(Exceptions.CODE_ERROR_PARAMETER, "roleId,roleUuid参数错误。");
        }
        return service().findPage4KhRole(t, page);
    }

    @Override
    public Page<Long, Application> findPage4KhCompany(Trace t, Page<Long, Application> page) throws IllegalParameterException {
        if (!page.hasRule4Field("khCompanyId") || !page.hasRule4Field("khCompanyUuid")) {
            throw new IllegalParameterException(Exceptions.CODE_ERROR_PARAMETER,
                    "khCompanyId,khCompanyUuid参数错误。");
        }
        return service().findPage4KhCompany(t, page);
    }

	@Override
	public List<Application> find4YwUser(Trace t, Long ywUserId) {
		return service().find4YwUser(t, ywUserId);
	}

	@Override
	public List<Application> find4KhUser(Trace t, Long khUserId) {
		return service().find4KhUser(t, khUserId);
	}

	@Override
	public Boolean updateInterfaceInfo(Trace t, Application app) {
		return service().updateInterfaceInfo(t, app);
	}

}
