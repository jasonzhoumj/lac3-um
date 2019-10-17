package com.linkallcloud.um.server.service.sys;

import java.util.List;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.dto.Tree;
import com.linkallcloud.service.ITreeService;
import com.linkallcloud.um.domain.sys.Menu;

public interface IMenuService extends ITreeService<Long, Menu> {

    List<Menu> findAppMenus(Trace t, Long appId, boolean valid);

    /**
     * 获取应用的菜单树
     * 
     * @param tid
     * @param appCode
     * @return
     */
    Tree getMenuTree(Trace t, String appCode);

    /**
     * 获取应用的菜单
     * 
     * @param tid
     * @param appId
     * @return
     */
    List<Tree> getMenus(Trace t, Long appId);

    /**
     * 获取应用有效状态的菜单
     * 
     * @param tid
     * @param appId
     * @param appUuid
     * @return
     */
    List<Tree> getValidMenus(Trace t, Long appId);
    List<Tree> getValidMenusWithButton(Trace t, Long appId);

    // List<Tree> findKhCompanyValidMenus(Trace t, Long companyId, Long appId);

    // List<Tree> findYwCompanyValidMenus(Trace t, Long ywCompanyId, Long appId, String appUuid);

}
