package com.linkallcloud.um.server.manager.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.dubbo.config.annotation.Service;
import com.linkallcloud.core.busilog.annotation.Module;
import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.dto.Tree;
import com.linkallcloud.core.manager.TreeDomainManager;
import com.linkallcloud.um.domain.sys.Menu;
import com.linkallcloud.um.iapi.sys.IMenuManager;
import com.linkallcloud.um.server.service.sys.IMenuService;

@Service(interfaceClass = IMenuManager.class, version = "${dubbo.service.version}")
@Component
@Module(name = "菜单")
public class MenuManager extends TreeDomainManager<Long, Menu, IMenuService> implements IMenuManager {

    @Autowired
    private IMenuService menuService;

    @Override
    public List<Tree> getMenus(Trace t, Long appId) {
        return service().getMenus(t, appId);
    }

    @Override
    public List<Tree> getValidMenus(Trace t, Long appId) {
        return service().getValidMenus(t, appId);
    }

    @Override
    protected IMenuService service() {
        return menuService;
    }

    @Override
    public Tree getMenuTree(Trace t, String appCode) {
        return service().getMenuTree(t, appCode);
    }

}
