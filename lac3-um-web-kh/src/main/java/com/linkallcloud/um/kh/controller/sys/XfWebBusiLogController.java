package com.linkallcloud.um.kh.controller.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.linkallcloud.comm.web.controller.BaseFullWebBusiLogController;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.query.Query;
import com.linkallcloud.um.domain.sys.XfWebBusiLog;
import com.linkallcloud.um.excel.LogExport;
import com.linkallcloud.um.iapi.sys.ILacWebBusiLogManager;
import com.linkallcloud.um.kh.utils.FileUtil;

@Controller
@RequestMapping(value = "/log", method = RequestMethod.POST)
public class XfWebBusiLogController extends BaseFullWebBusiLogController<XfWebBusiLog, ILacWebBusiLogManager> {

    @Reference(version = "${dubbo.service.version}", application = "${dubbo.application.id}")
	private ILacWebBusiLogManager lacWebBusiLogManager;

	@Override
	protected ILacWebBusiLogManager manager() {
		return lacWebBusiLogManager;
	}

	@RequestMapping(value = "export", method = RequestMethod.POST)
	public void export(HttpServletResponse response,
			@RequestParam(value = "searchCnds", required = false) String searchCnds, Trace t) {
		String fileName = "操作日志_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls";
		Query query = JSON.parseObject(searchCnds, Query.class);
		List<XfWebBusiLog> logs = manager().find(t, query);
		List<LogExport> list = new ArrayList<LogExport>();
		if (logs != null && logs.size() > 0) {
			for (XfWebBusiLog blog : logs) {
				LogExport le = new LogExport(blog);
				list.add(le);
			}
		}
		FileUtil.exportExcel(list, null, "操作日志", LogExport.class, fileName, response);
	}

}
