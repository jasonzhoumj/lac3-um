package com.linkallcloud.um.server.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.linkallcloud.dao.ITreeDao;
import com.linkallcloud.dto.Trace;
import com.linkallcloud.um.domain.sys.Dict;

public interface IDictDao extends ITreeDao<Long, Dict> {

	List<Dict> getDictsByTypeId(@Param("t") Trace t, @Param("typeId") Long typeId);
	
	List<Dict> getDictsByTopTypeId(@Param("t") Trace t, @Param("topTypeId") Long topTypeId);

}