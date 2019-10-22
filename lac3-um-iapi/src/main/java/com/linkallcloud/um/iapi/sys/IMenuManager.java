package com.linkallcloud.um.iapi.sys;

import java.util.List;

import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.dto.Tree;
import com.linkallcloud.core.manager.ITreeManager;
import com.linkallcloud.um.domain.sys.Menu;

public interface IMenuManager extends ITreeManager<Menu> {

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
     * @return
     */
    List<Tree> getValidMenus(Trace t, Long appId);

    /**
     * 获取应用的菜单树
     * 
     * @param tid
     * @param appCode
     * @return
     */
    Tree getMenuTree(Trace t, String appCode);

}
