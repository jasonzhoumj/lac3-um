package com.linkallcloud.um.server.activity.sys;

import com.linkallcloud.core.activity.BaseActivity;
import com.linkallcloud.um.activity.sys.IXfWebBusiLogActivity;
import com.linkallcloud.um.domain.sys.XfWebBusiLog;
import com.linkallcloud.um.server.dao.sys.IXfWebBusiLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XfWebBusiLogActivity extends BaseActivity<XfWebBusiLog, IXfWebBusiLogDao> implements IXfWebBusiLogActivity {

    @Autowired
    private IXfWebBusiLogDao xfWebBusiLogDao;

    @Override
    public IXfWebBusiLogDao dao() {
        return xfWebBusiLogDao;
    }
}
