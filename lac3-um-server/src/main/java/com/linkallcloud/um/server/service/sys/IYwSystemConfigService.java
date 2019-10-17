package com.linkallcloud.um.server.service.sys;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.service.IService;
import com.linkallcloud.um.domain.sys.YwSystemConfig;

public interface IYwSystemConfigService extends IService<Long, YwSystemConfig> {

    YwSystemConfig fetchByCompanyId(Trace t, Long companyId);

}
