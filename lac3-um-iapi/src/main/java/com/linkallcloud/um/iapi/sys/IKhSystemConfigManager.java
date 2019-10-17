package com.linkallcloud.um.iapi.sys;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.manager.IManager;
import com.linkallcloud.um.domain.sys.KhSystemConfig;

public interface IKhSystemConfigManager extends IManager<Long, KhSystemConfig> {

    KhSystemConfig fetchByCompanyId(Trace t, Long companyId);

    Long save(Trace t, KhSystemConfig entity);

}
