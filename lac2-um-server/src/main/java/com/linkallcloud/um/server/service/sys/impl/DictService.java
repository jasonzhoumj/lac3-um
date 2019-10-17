package com.linkallcloud.um.server.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkallcloud.busilog.annotation.Module;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.BaseRuntimeException;
import com.linkallcloud.exception.Exceptions;
import com.linkallcloud.lang.Strings;
import com.linkallcloud.service.TreeDomainService;
import com.linkallcloud.um.domain.sys.Dict;
import com.linkallcloud.um.domain.sys.DictType;
import com.linkallcloud.um.server.dao.sys.IDictDao;
import com.linkallcloud.um.server.dao.sys.IDictTypeDao;
import com.linkallcloud.um.server.service.sys.IDictService;

@Module(name = "数据字典")
@Service
@Transactional(readOnly = true)
public class DictService extends TreeDomainService<Long, Dict, IDictDao> implements IDictService {

	@Autowired
	private IDictDao dictDao;

	@Autowired
	private IDictTypeDao dictTypeDao;

	@Cacheable(value = "Dicts", key = "#dictTypeId")
	@Override
	public List<Dict> findDictsByDictTypeId(Trace t, Long dictTypeId) {
		return dao().getDictsByTypeId(t, dictTypeId);
	}

	@Override
	public IDictDao dao() {
		return dictDao;
	}

	@Cacheable(value = "Dicts-Top", key = "#topDictTypeId")
	@Override
	public List<Dict> findDictsByDictTopTypeId(Trace t, Long topDictTypeId) {
		return dao().getDictsByTopTypeId(t, topDictTypeId);
	}

	@Override
	protected void treeBefore(Trace t, boolean isNew, Dict entity) {
		if (isNew) {
			if (entity.getParentId() == null || entity.getParentId() <= 0L) {
				throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER, "parentId参数错误。");
			}

			if (!Strings.isBlank(entity.getGovCode())) {
				Dict dbEntity = dao().fetchByGovCode(t, entity.getGovCode());
				if (dbEntity != null) {
					throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER,
							"已经存在相同govCode的节点：" + entity.getGovCode());
				}
			}

			DictType parent = dictTypeDao.fetchById(t, entity.getParentId());
			Long topParentId = Long.valueOf(parent.getCode().toString().split(parent.codeTag())[0]);
			entity.setTopTypeId(topParentId);
		}
	}

	@Override
	protected void treeAfter(Trace t, boolean isNew, Dict entity) {

	}

	@CacheEvict(value = "Dicts-Top", key = "#topDictTypeId")
	@Override
	public void cleanDictsTopCache(Trace t, Long topDictTypeId) {
	}

	@CacheEvict(value = "Dicts", key = "#dictTypeId")
	@Override
	public void cleanDictsCache(Trace t, Long dictTypeId) {
	}

}
