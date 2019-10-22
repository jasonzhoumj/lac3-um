package com.linkallcloud.um.service.party;

import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.exception.BaseRuntimeException;
import com.linkallcloud.um.domain.party.Org;

import java.util.Map;

public interface IOrgService<T extends Org> extends IPartyService<T> {

	boolean addLeaders(Trace t, Long orgId, String orgUuid, Map<String, Long> userUuidIds);

	boolean deleteLeaders(Trace t, Long orgId, String orgUuid, Map<String, Long> userUuidIds);

}
