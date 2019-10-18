package com.linkallcloud.um.server.service.party;

import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.service.ITreeService;
import com.linkallcloud.um.domain.party.Party;

public interface IPartyService<T extends Party> extends ITreeService<Long, T> {

	/**
	 * 根据行政编码查询
	 * 
	 * @param tid
	 * @param govCode
	 *            Company:编码；Department：部门编号；User：员工工号
	 * @return
	 */
	T fetchByGovCode(Trace t, String govCode);

}
