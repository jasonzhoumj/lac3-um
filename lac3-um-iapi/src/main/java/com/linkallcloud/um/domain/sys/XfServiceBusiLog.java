package com.linkallcloud.um.domain.sys;

import com.linkallcloud.laclog.ServiceBusiLog;

public class XfServiceBusiLog extends ServiceBusiLog<Long> {
	private static final long serialVersionUID = -6207912802597778114L;

	public XfServiceBusiLog() {
		super();
	}

	public XfServiceBusiLog(Long id, String uuid) {
		super(id, uuid);
	}

	public XfServiceBusiLog(Long id) {
		super(id);
	}

}
