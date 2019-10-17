package com.linkallcloud.um.server.service.party;

import java.util.Map;

import com.linkallcloud.dto.Trace;
import com.linkallcloud.exception.BaseRuntimeException;
import com.linkallcloud.um.domain.party.Org;

public interface IOrgService<T extends Org> extends IPartyService<T> {

	boolean addLeaders(Trace t, Long orgId, String orgUuid, Map<String, Long> userUuidIds)
			throws BaseRuntimeException;

	boolean deleteLeaders(Trace t, Long orgId, String orgUuid, Map<String, Long> userUuidIds)
			throws BaseRuntimeException;

}
