package com.linkallcloud.um.server.service.sys;

import java.util.List;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.service.ITreeService;
import com.linkallcloud.um.domain.sys.Dict;

public interface IDictService extends ITreeService<Long, Dict> {
	
	/**
	 * 根据dictTypeId，得到其下直接的字典数据
	 * 
	 * @param t
	 * @param dictTypeId
	 * @return
	 */
	List<Dict> findDictsByDictTypeId(Trace t, Long dictTypeId);
	
	/**
	 * 根据topDictTypeId，得到其下所有分类的字段数据
	 * 
	 * @param t
	 * @param topDictTypeId
	 * @return
	 */
	List<Dict> findDictsByDictTopTypeId(Trace t, Long topDictTypeId);
	
	void cleanDictsTopCache(Trace t, Long topDictTypeId);
	void cleanDictsCache(Trace t, Long dictTypeId);

}
