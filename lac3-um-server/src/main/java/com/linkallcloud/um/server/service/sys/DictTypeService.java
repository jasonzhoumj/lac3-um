package com.linkallcloud.um.server.service.sys;

import com.linkallcloud.core.busilog.annotation.ServLog;
import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.dto.Tree;
import com.linkallcloud.core.dto.Trees;
import com.linkallcloud.core.exception.BaseRuntimeException;
import com.linkallcloud.core.service.BaseTreeService;
import com.linkallcloud.um.activity.sys.IDictTypeActivity;
import com.linkallcloud.um.domain.sys.DictType;
import com.linkallcloud.um.service.sys.IDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DictTypeService extends BaseTreeService<DictType, IDictTypeActivity> implements IDictTypeService {

    @Autowired
    private IDictTypeActivity dictTypeActivity;

    @Override
    public IDictTypeActivity activity() {
        return dictTypeActivity;
    }

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
        return activity().fetchByGovCode(t, dictTypeGovCode);
    }

    @Cacheable(value = "DictTypeTree", key = "#dictTypeId")
    @Override
    public Tree getDictTypeTreeWithDictsById(Trace t, Long dictTypeId) {
        return activity().getDictTypeTreeWithDictsById(t, dictTypeId);
    }

    @Cacheable(value = "DictTree", key = "#dictTypeId")
    @Override
    public Tree getDirectDictsByTypeId(Trace t, Long dictTypeId) {
        return activity().getDirectDictsByTypeId(t, dictTypeId);
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

}
