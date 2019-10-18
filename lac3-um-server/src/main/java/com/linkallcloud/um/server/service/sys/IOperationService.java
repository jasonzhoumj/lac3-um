package com.linkallcloud.um.server.service.sys;

import java.util.List;
import java.util.Map;

import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.service.IService;
import com.linkallcloud.um.domain.sys.Operation;

public interface IOperationService extends IService<Long, Operation> {

	List<Operation> findByMenuId(Trace t, Long menuId);

	List<Operation> findByAppId(Trace t, Long appId);

    Map<String, String[]> loadAppUriRescodeMap(Trace t, Long appId);

}
