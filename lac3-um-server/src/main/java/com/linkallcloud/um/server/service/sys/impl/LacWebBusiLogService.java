package com.linkallcloud.um.server.service.sys.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkallcloud.core.service.BusiLogBaseService;
import com.linkallcloud.um.domain.sys.XfWebBusiLog;
import com.linkallcloud.um.server.dao.sys.IXfWebBusiLogDao;
import com.linkallcloud.um.server.service.sys.ILacWebBusiLogService;

@Service
@Transactional(readOnly = true)
public class LacWebBusiLogService extends BusiLogBaseService<Long, XfWebBusiLog, IXfWebBusiLogDao>
		implements ILacWebBusiLogService {

	@Autowired
	private IXfWebBusiLogDao xfWebBusiLogDao;

	@Override
	public IXfWebBusiLogDao dao() {
		return xfWebBusiLogDao;
	}

}
