package com.linkallcloud.um.domain.sys;

import com.linkallcloud.laclog.WebBusiLog;

public class XfWebBusiLog extends WebBusiLog<Long> {
	private static final long serialVersionUID = 4551689842447942150L;

	public XfWebBusiLog() {
		super();
	}

	public XfWebBusiLog(Long id, String uuid) {
		super(id, uuid);
	}

	public XfWebBusiLog(Long id) {
		super(id);
	}

}