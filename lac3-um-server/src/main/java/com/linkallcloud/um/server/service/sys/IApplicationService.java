package com.linkallcloud.um.server.service.sys;

import java.util.List;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.pagination.Page;
import com.linkallcloud.service.IService;
import com.linkallcloud.um.domain.sys.Application;

public interface IApplicationService extends IService<Long, Application> {

    Application fetchByCode(Trace t, String code);
    
    Boolean updateInterfaceInfo(Trace t, Application app);

    /**
     * 查询某角色许可的应用
     * 
     * @param tid
     * @param page
     *            查询条件中必须包含：roleId和roleUuid参数
     * 
     * @return
     */
    Page<Long, Application> findPage4YwRole(Trace t, Page<Long, Application> page);

    Page<Long, Application> findPage4KhRole(Trace t, Page<Long, Application> page);

    Page<Long, Application> findPage4KhCompany(Trace t, Page<Long, Application> page);
    
    List<Application> find4YwUser(Trace t, Long ywUserId);
    List<Application> find4KhUser(Trace t, Long khUserId);

}
