package com.linkallcloud.um.server.service.sys;

import com.linkallcloud.core.service.BaseBusiLogService;
import com.linkallcloud.um.activity.sys.IXfServiceBusiLogActivity;
import com.linkallcloud.um.domain.sys.XfServiceBusiLog;
import com.linkallcloud.um.service.sys.ILacServiceBusiLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LacServiceBusiLogService extends BaseBusiLogService<XfServiceBusiLog, IXfServiceBusiLogActivity>
        implements ILacServiceBusiLogService {

    @Autowired
    private IXfServiceBusiLogActivity xfServiceBusiLogActivity;

    @Override
    public IXfServiceBusiLogActivity activity() {
        return xfServiceBusiLogActivity;
    }

}
