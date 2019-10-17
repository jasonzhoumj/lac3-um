package com.linkallcloud.um.server.manager.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.linkallcloud.busilog.annotation.Module;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.dto.Tree;
import com.linkallcloud.exception.BaseRuntimeException;
import com.linkallcloud.manager.TreeDomainManager;
import com.linkallcloud.query.rule.QueryRule;
import com.linkallcloud.um.domain.sys.Area;
import com.linkallcloud.um.dto.base.PermedAreaVo;
import com.linkallcloud.um.iapi.sys.IAreaManager;
import com.linkallcloud.um.server.service.sys.IAreaService;

@Service(interfaceClass = IAreaManager.class, version = "${dubbo.service.version}")
@Component
@Module(name = "区域")
public class AreaManager extends TreeDomainManager<Long, Area, IAreaService> implements IAreaManager {

	@Autowired
	private IAreaService areaService;

	@Override
	protected IAreaService service() {
		return areaService;
	}

	@Override
	public Area fetchByGovCode(Trace t, String areaRootGovCode) {
		return service().fetchByGovCode(t, areaRootGovCode);
	}

	@Override
	public int getChildrenCount(Trace t, Long parenId, QueryRule statusRule) {
		return service().getChildrenCount(t, parenId, statusRule);
	}

	@Override
	public List<Area> findChildren(Trace t, String parentCode, QueryRule statusRule) {
		return service().findChildren(t, parentCode, statusRule);
	}

	@Override
	public List<Tree> findChildrenTreeNodes(Trace t, String areaRootCode, QueryRule statusRule) {
		Area root = service().fetchByGovCode(t, areaRootCode);
		if (root == null) {
			throw new BaseRuntimeException("10001", "areaRootCode参数错误：" + areaRootCode);
		}
		return service().findChildrenTreeNodes(t, root.getId(), statusRule);
	}

	@Override
	public List<Tree> findChildrenTreeNodes(Trace t, Long areaRootId, QueryRule statusRule) {
		return service().findChildrenTreeNodes(t, areaRootId, statusRule);
	}

	@Override
	public List<Area> findDirectChildren(Trace t, Long parentId, QueryRule statusRule) {
		return service().findDirectChildren(t, parentId, statusRule);
	}

	@Override
	public List<Tree> findDirectChildrenTreeNodes(Trace t, Long parentId, QueryRule statusRule) {
		return service().findDirectChildrenTreeNodes(t, parentId, statusRule);
	}

	@Override
	public List<Area> findByParentCodeAndLevel(Trace t, String parentCode, int levelLt) {
		return service().findByParentCodeAndLevel(t, parentCode, levelLt);
	}

	@Override
	public List<Area> findPermedKhCompanyAppAreas(Trace t, Long khCompanyId, Long appId) {
		return service().findPermedKhCompanyAppAreas(t, khCompanyId, appId);
	}

	@Override
	public List<Area> findPermedYwCompanyAppAreas(Trace t, Long ywCompanyId, Long appId) {
		return service().findPermedYwCompanyAppAreas(t, ywCompanyId, appId);
	}

	@Override
	public PermedAreaVo findValidAreaResourceByParent(Trace t, Long parentAreaId) {
		return service().findValidAreaResourceByParent(t, parentAreaId);
	}

}
