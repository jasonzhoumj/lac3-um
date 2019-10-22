package com.linkallcloud.um.server.manager.sys;

import com.linkallcloud.um.service.sys.ILacWebBusiLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.dubbo.config.annotation.Service;
import com.linkallcloud.core.busilog.annotation.Module;
import com.linkallcloud.core.manager.BaseManager;
import com.linkallcloud.um.domain.sys.XfWebBusiLog;
import com.linkallcloud.um.iapi.sys.ILacWebBusiLogManager;

@Service(interfaceClass = ILacWebBusiLogManager.class, version = "${dubbo.service.version}")
@Component
@Module(name = "Web层日志")
public class LacWebBusiLogManager extends BaseManager<XfWebBusiLog, ILacWebBusiLogService>
		implements ILacWebBusiLogManager {

	@Autowired
	private ILacWebBusiLogService lacWebBusiLogService;

	@Override
	protected ILacWebBusiLogService service() {
		return lacWebBusiLogService;
	}

}
