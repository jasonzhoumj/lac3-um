package com.linkallcloud.um.server.service.sys;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.service.IService;
import com.linkallcloud.um.domain.sys.KhSystemConfig;

public interface IKhSystemConfigService extends IService<Long, KhSystemConfig> {

    KhSystemConfig fetchByCompanyId(Trace t, Long companyId);

}
