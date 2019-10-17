package com.linkallcloud.um.server.dao.sys;

import org.apache.ibatis.annotations.Param;

import com.linkallcloud.dao.IDao;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.um.domain.sys.KhSystemConfig;

public interface IKhSystemConfigDao extends IDao<Long, KhSystemConfig> {

    KhSystemConfig fetchByCompanyId(@Param("t") Trace t, @Param("companyId") Long companyId);

}
