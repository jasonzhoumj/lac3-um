package com.linkallcloud.um.iapi.sys;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.manager.IManager;
import com.linkallcloud.um.domain.sys.YwSystemConfig;

public interface IYwSystemConfigManager extends IManager<Long, YwSystemConfig> {

    YwSystemConfig fetchByCompanyId(Trace t, Long companyId);

    Long save(Trace t, YwSystemConfig entity);

}
