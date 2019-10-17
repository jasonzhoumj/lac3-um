package com.linkallcloud.um.server.manager.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.linkallcloud.busilog.annotation.Module;
import com.linkallcloud.manager.BaseManager;
import com.linkallcloud.um.domain.sys.XfWebBusiLog;
import com.linkallcloud.um.iapi.sys.ILacWebBusiLogManager;
import com.linkallcloud.um.server.service.sys.ILacWebBusiLogService;

@Service(interfaceClass = ILacWebBusiLogManager.class, version = "${dubbo.service.version}")
@Component
@Module(name = "Web层日志")
public class LacWebBusiLogManager extends BaseManager<Long, XfWebBusiLog, ILacWebBusiLogService>
		implements ILacWebBusiLogManager {

	@Autowired
	private ILacWebBusiLogService lacWebBusiLogService;

	@Override
	protected ILacWebBusiLogService service() {
		return lacWebBusiLogService;
	}

}
