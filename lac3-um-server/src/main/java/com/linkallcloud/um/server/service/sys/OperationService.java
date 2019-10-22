package com.linkallcloud.um.server.service.sys;

import com.linkallcloud.core.busilog.annotation.Module;
import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.service.BaseService;
import com.linkallcloud.um.activity.sys.IOperationActivity;
import com.linkallcloud.um.domain.sys.Operation;
import com.linkallcloud.um.service.sys.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Module(name = "操作")
@Service
@Transactional(readOnly = true)
public class OperationService extends BaseService<Operation, IOperationActivity> implements IOperationService {

    @Autowired
    private IOperationActivity operationActivity;

    @Override
    public IOperationActivity activity() {
        return operationActivity;
    }

    @Override
    public List<Operation> findByMenuId(Trace t, Long menuId) {
        return activity().findByMenuId(t, menuId);
    }

    @Override
    public List<Operation> findByAppId(Trace t, Long appId) {
        return activity().findByAppId(t, appId);
    }

    //@Cacheable(value = "LAC", key = "\"AppUriRescodeMap-\" + #appId")
    @Override
    public Map<String, String[]> loadAppUriRescodeMap(Trace t, Long appId) {
        return activity().loadAppUriRescodeMap(t, appId);
    }

}
