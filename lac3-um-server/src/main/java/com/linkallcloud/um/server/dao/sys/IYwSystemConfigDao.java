package com.linkallcloud.um.server.dao.sys;

import org.apache.ibatis.annotations.Param;

import com.linkallcloud.dao.IDao;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.um.domain.sys.YwSystemConfig;

public interface IYwSystemConfigDao extends IDao<Long, YwSystemConfig> {

    YwSystemConfig fetchByCompanyId(@Param("t") Trace t, @Param("companyId") Long companyId);

}