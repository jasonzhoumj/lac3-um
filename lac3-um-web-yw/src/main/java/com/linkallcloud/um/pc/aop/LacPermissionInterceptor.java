package com.linkallcloud.um.pc.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import com.linkallcloud.web.interceptors.PermissionInterceptor;
import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.um.iapi.sys.IOperationManager;

public class LacPermissionInterceptor extends PermissionInterceptor {

    private static Map<Long, Map<String, String[]>> appUriRescodeMap = new HashMap<>();

    @Reference(version = "${dubbo.service.version}", application = "${dubbo.application.id}")
    private IOperationManager operationManager;

    public LacPermissionInterceptor() {
        super();
    }

    public LacPermissionInterceptor(List<String> ignoreRes, boolean override, String login, String noPermission) {
        super(ignoreRes, override, login, noPermission);
    }

    public LacPermissionInterceptor(List<String> ignoreRes, boolean override) {
        super(ignoreRes, override);
    }

    public LacPermissionInterceptor(String login, String noPermission) {
        super(login, noPermission);
    }

    @Override
    protected Map<String, String[]> loadAppUriRescodeMap(Trace t, Long appId) {
        if (appUriRescodeMap.containsKey(appId)) {
            return appUriRescodeMap.get(appId);
        } else {
            Map<String, String[]> thisRes = operationManager.loadAppUriRescodeMap(t, appId);
            appUriRescodeMap.put(appId, thisRes);
            return thisRes;
        }
    }

}
