package com.linkallcloud.um.server.service.sys;

import com.linkallcloud.core.service.BaseBusiLogService;
import com.linkallcloud.um.activity.sys.IXfWebBusiLogActivity;
import com.linkallcloud.um.domain.sys.XfWebBusiLog;
import com.linkallcloud.um.service.sys.ILacWebBusiLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LacWebBusiLogService extends BaseBusiLogService<XfWebBusiLog, IXfWebBusiLogActivity>
        implements ILacWebBusiLogService {

    @Autowired
    private IXfWebBusiLogActivity xfWebBusiLogActivity;

    @Override
    public IXfWebBusiLogActivity activity() {
        return xfWebBusiLogActivity;
    }

}
