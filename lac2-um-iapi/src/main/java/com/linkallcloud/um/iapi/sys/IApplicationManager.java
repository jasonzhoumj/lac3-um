package com.linkallcloud.um.iapi.sys;

import java.util.List;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.IllegalParameterException;
import com.linkallcloud.manager.IManager;
import com.linkallcloud.pagination.Page;
import com.linkallcloud.um.domain.sys.Application;

public interface IApplicationManager extends IManager<Long, Application> {

    Application fetchByCode(Trace t, String code);
    
    Boolean updateInterfaceInfo(Trace t, Application app); 

    Page<Long, Application> findPage4YwRole(Trace t, Page<Long, Application> page) throws IllegalParameterException;

    Page<Long, Application> findPage4KhRole(Trace t, Page<Long, Application> page) throws IllegalParameterException;

    Page<Long, Application> findPage4KhCompany(Trace t, Page<Long, Application> page) throws IllegalParameterException;
    
    List<Application> find4YwUser(Trace t, Long ywUserId);
    List<Application> find4KhUser(Trace t, Long khUserId);

}
