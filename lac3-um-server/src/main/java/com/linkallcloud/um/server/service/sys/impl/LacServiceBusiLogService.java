package com.linkallcloud.um.server.service.sys.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkallcloud.core.service.BusiLogBaseService;
import com.linkallcloud.um.domain.sys.XfServiceBusiLog;
import com.linkallcloud.um.server.dao.sys.IXfServiceBusiLogDao;
import com.linkallcloud.um.server.service.sys.ILacServiceBusiLogService;

@Service
@Transactional(readOnly = true)
public class LacServiceBusiLogService extends BusiLogBaseService<Long, XfServiceBusiLog, IXfServiceBusiLogDao>
		implements ILacServiceBusiLogService {

	@Autowired
	private IXfServiceBusiLogDao xfServiceBusiLogDao;

	@Override
	public IXfServiceBusiLogDao dao() {
		return xfServiceBusiLogDao;
	}

}
