package com.linkallcloud.um.iapi.sys;

import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.manager.IManager;
import com.linkallcloud.um.domain.sys.YwSystemConfig;

public interface IYwSystemConfigManager extends IManager<Long, YwSystemConfig> {

    YwSystemConfig fetchByCompanyId(Trace t, Long companyId);

    Long save(Trace t, YwSystemConfig entity);

}
