package com.linkallcloud.um.server.service.party.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.BaseException;
import com.linkallcloud.pagination.Page;
import com.linkallcloud.um.domain.party.KhCompany;
import com.linkallcloud.um.domain.party.KhDepartment;
import com.linkallcloud.um.domain.party.KhRole;
import com.linkallcloud.um.domain.party.KhUser;
import com.linkallcloud.um.server.dao.party.IKhCompanyDao;
import com.linkallcloud.um.server.dao.party.IKhDepartmentDao;
import com.linkallcloud.um.server.dao.party.IKhRoleDao;
import com.linkallcloud.um.server.dao.party.IKhUserDao;
import com.linkallcloud.um.server.dao.party.IYwCompanyDao;
import com.linkallcloud.um.server.dao.party.IYwUserDao;
import com.linkallcloud.um.server.dao.sys.IAreaDao;
import com.linkallcloud.um.server.service.party.IKhUserService;
import com.linkallcloud.um.server.utils.UmTools;

@Service
@Transactional(readOnly = true)
public class KhUserService extends
        UserService<KhUser, IKhUserDao, KhDepartment, IKhDepartmentDao, KhCompany, IKhCompanyDao, KhRole, IKhRoleDao>
        implements IKhUserService {

    @Autowired
    private IKhUserDao khUserDao;

    @Autowired
    private IKhDepartmentDao khDepartmentDao;

    @Autowired
    private IKhCompanyDao khCompanyDao;

    @Autowired
    private IKhRoleDao khRoleDao;
    
    @Autowired
	private IYwCompanyDao ywCompanyDao;

    @Autowired
    private IYwUserDao ywUserDao;

    @Autowired
    private IAreaDao areaDao;

    @Override
    public IKhUserDao dao() {
        return khUserDao;
    }

    @Override
    protected IKhDepartmentDao getDepartmentDao() {
        return khDepartmentDao;
    }

    @Override
    protected IKhCompanyDao getCompanyDao() {
        return khCompanyDao;
    }

    @Override
    protected IKhRoleDao getRoleDao() {
        return khRoleDao;
    }

    /**
     * 根据user的区域权限，查找用户
     * 
     * @param t
     * @param page
     *            必须包含ywUserId,appId参数条件
     * @return
     */
    @Override
    public Page<Long, KhUser> findPage(Trace t, Page<Long, KhUser> page) {
        try {
            UmTools.addAreaCnds4YwUserAppPermission(t, page, ywCompanyDao, ywUserDao, areaDao);
        } catch (BaseException e) {
            return page;
        }
        return super.findPage(t, page);
    }

    /**
     * 根据user的区域权限，查找用户
     * 
     * @param t
     * @param page
     *            必须包含ywUserId,appId参数条件
     * @return
     */
    @Override
    public Page<Long, KhUser> findPage4Select(Trace t, Page<Long, KhUser> page) {
        try {
            UmTools.addAreaCnds4YwUserAppPermission(t, page, ywCompanyDao, ywUserDao, areaDao);
        } catch (BaseException e) {
            return page;
        }
        return super.findPage4Select(t, page);
    }

    @Override
    public Page<Long, KhUser> findSelfUserPage(Trace t, Page<Long, KhUser> page) {
        page.checkPageParameters();
        try {
            PageHelper.startPage(page.getPageNum(), page.getLength());
            List<KhUser> list = dao().findSelfUserPage(t, page);
            if (list instanceof com.github.pagehelper.Page) {
                page.setRecordsTotal(((com.github.pagehelper.Page<KhUser>) list).getTotal());
                page.checkPageParameters();
                page.setRecordsFiltered(page.getRecordsTotal());
                page.addDataAll(list);
            }
            return page;
        } finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public Page<Long, KhUser> findPermedSelfUserPage(Trace t, Page<Long, KhUser> page) {
        page.checkPageParameters();
        try {
            PageHelper.startPage(page.getPageNum(), page.getLength());
            List<KhUser> list = dao().findPermedSelfUserPage(t, page);
            if (list instanceof com.github.pagehelper.Page) {
                page.setRecordsTotal(((com.github.pagehelper.Page<KhUser>) list).getTotal());
                page.checkPageParameters();
                page.setRecordsFiltered(page.getRecordsTotal());
                page.addDataAll(list);
            }
            return page;
        } finally {
            PageHelper.clearPage();
        }
    }

}
