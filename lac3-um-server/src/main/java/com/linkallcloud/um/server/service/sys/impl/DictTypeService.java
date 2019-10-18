package com.linkallcloud.um.server.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkallcloud.core.busilog.annotation.ServLog;
import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.dto.Tree;
import com.linkallcloud.core.dto.Trees;
import com.linkallcloud.core.exception.BaseRuntimeException;
import com.linkallcloud.core.service.TreeDomainService;
import com.linkallcloud.um.domain.sys.Dict;
import com.linkallcloud.um.domain.sys.DictType;
import com.linkallcloud.um.server.dao.sys.IDictDao;
import com.linkallcloud.um.server.dao.sys.IDictTypeDao;
import com.linkallcloud.um.server.service.sys.IDictTypeService;

@Service
@Transactional(readOnly = true)
public class DictTypeService extends TreeDomainService<Long, DictType, IDictTypeDao> implements IDictTypeService {

	@Autowired
	private IDictTypeDao dictTypeDao;

	@Autowired
	private IDictDao dictDao;

	@Override
	public List<Tree> getTreeNodes(Trace t, boolean valid) {
		List<Tree> result = super.getTreeNodes(t, valid);
		Tree root = new Tree("DICT_TYPE_ROOT", null, "所有数据字典分类");
		root.setOpen(true);
		result = Trees.assembleChildren2Parent(result, root);
		return result;
	}

	@Override
	public Tree getTree(Trace t, boolean valid) {
		Tree tree = super.getTree(t, valid);
		tree.setOpen(true);
		tree.setId("DICT_TYPE_ROOT");
		tree.setName("所有数据字典分类");
		return tree;
	}

	@Override
	public DictType fetchByGovCode(Trace t, String dictTypeGovCode) {
		return dao().fetchByGovCode(t, dictTypeGovCode);
	}

	@Cacheable(value = "DictTypeTree", key = "#dictTypeId")
	@Override
	public Tree getDictTypeTreeWithDictsById(Trace t, Long dictTypeId) {
		Tree root = null;
		DictType dt = dao().fetchById(t, dictTypeId);
		if (dt != null) {
			root = dt.toTreeNode();
			// root.setpId(null);

			List<DictType> children = dao().getChildrenByTopParentId(t, dt.getId());
			if (children != null && !children.isEmpty()) {
				Trees.assembleDomain2Tree(root, children);
			}

			List<Dict> dicts = dictDao.getDictsByTopTypeId(t, dt.getId());
			if (dicts != null && !dicts.isEmpty()) {
				Trees.assembleDomain2Tree(root, dicts);
			}
		}
		root.setpId(null);
		return root;
	}

	@Cacheable(value = "DictTree", key = "#dictTypeId")
	@Override
	public Tree getDirectDictsByTypeId(Trace t, Long dictTypeId) {
		Tree root = null;
		DictType dt = dao().fetchById(t, dictTypeId);
		if (dt != null) {
			root = dt.toTreeNode();
			// root.setpId(null);

			List<Dict> dicts = dictDao.getDictsByTypeId(t, dt.getId());
			if (dicts != null && !dicts.isEmpty()) {
				Trees.assembleDomain2Tree(root, dicts);
			}
		}
		root.setpId(null);
		return root;
	}

	@CacheEvict(value = "DictTree", key = "#dictTypeId")
	@Override
	public void cleanDictCache(Trace t, Long dictTypeId) {
	}

	@CacheEvict(value = "DictTypeTree", key = "#topDictTypeId")
	@Override
	public void cleanDictTypeCache(Trace t, Long topDictTypeId) {
	}

//	@Caching(evict = { @CacheEvict(value = "Dict", key = "\"Tree-\" + #entity.id"),
//			@CacheEvict(value = "Dict", key = "\"TypeTree-\" + #entity.id") })
	@Transactional(readOnly = false)
	@Override
	@ServLog(db = true)
	public boolean update(Trace t, DictType entity) throws BaseRuntimeException {
		return super.update(t, entity);
	}

//	@CacheEvict(value = { "Dict", "DictTree" }, key = "#id")
	@Transactional(readOnly = false)
	@Override
	@ServLog(db = true, desc = "删除 [(${domainShowName})]([(${id})]), TID:[(${tid})]")
	public boolean delete(Trace t, Long id, String uuid) throws BaseRuntimeException {
		return super.delete(t, id, uuid);
	}

	// @Cacheable(value = "DictTypeTreeWithDictsByTopCode", key =
	// "#topDictTypeCode")
	// @Override
	// public Tree getDictTypeTreeWithDictsByTopTypeCode(Trace t, String
	// topDictTypeCode) {
	// Tree root = null;
	// DictType dt = dao().fetchByGovCode(t, topDictTypeCode);
	// if (dt != null) {
	// root = dt.toTreeNode();
	// root.setpId(null);
	//
	// List<DictType> children = dao().getChildrenByTopParentId(t, dt.getId());
	// if (children != null && !children.isEmpty()) {
	// CopyOnWriteArrayList<DictType> types = new
	// CopyOnWriteArrayList<DictType>(children);
	// Trees.assembleTree(root, types);
	// }
	//
	// List<Dict> dicts = dictDao.getDictsByTopTypeId(t, dt.getId());
	// if (dicts != null && !dicts.isEmpty()) {
	// CopyOnWriteArrayList<Dict> dictList = new CopyOnWriteArrayList<Dict>(dicts);
	// Trees.assembleTree(root, dictList);
	// }
	// }
	// return root;
	// }

	// @Cacheable(value = "DictTypeTreeWithDictsByLeafCode", key =
	// "#leafDictTypeCode")
	// @Override
	// public Tree getDirectDictsByLeafTypeCode(Trace t, String leafDictTypeCode) {
	// Tree root = null;
	// DictType dt = dao().fetchByGovCode(t, leafDictTypeCode);
	// if (dt != null) {
	// root = dt.toTreeNode();
	// root.setpId(null);
	//
	// List<Dict> dicts = dictDao.getDictsByTypeId(t, dt.getId());
	// if (dicts != null && !dicts.isEmpty()) {
	// CopyOnWriteArrayList<Dict> dictList = new CopyOnWriteArrayList<Dict>(dicts);
	// Trees.assembleTree(root, dictList);
	// }
	// }
	// return root;
	// }

	@Override
	public IDictTypeDao dao() {
		return dictTypeDao;
	}

	@Override
	protected void before(Trace t, boolean isNew, DictType entity) throws BaseRuntimeException {
		super.before(t, isNew, entity);
		if (isNew) {
			if (entity.isTopParent()) {
				entity.setTopParentId(null);
			} else {
				DictType parent = dao().fetchById(t, entity.getParentId());
				if (parent != null) {
					Long topParentId = Long.valueOf(parent.getCode().toString().split(parent.codeTag())[0]);
					entity.setTopParentId(topParentId);
				}
			}
		}
	}

}
