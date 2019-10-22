package com.linkallcloud.um.service.sys;

import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.service.IService;
import com.linkallcloud.um.domain.sys.KhSystemConfig;

public interface IKhSystemConfigService extends IService<KhSystemConfig> {

    KhSystemConfig fetchByCompanyId(Trace t, Long companyId);

}
