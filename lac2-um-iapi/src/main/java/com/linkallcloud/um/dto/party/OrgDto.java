package com.linkallcloud.um.dto.party;

import com.linkallcloud.um.domain.party.Org;

public class OrgDto extends Org {
	private static final long serialVersionUID = 3120872901960385990L;

	private int leaders;// 领导数量

	public OrgDto() {
		super();
	}

	public OrgDto(Long id, String uuid) {
		super(id, uuid);
	}

	public OrgDto(Long id) {
		super(id);
	}

	public int getLeaders() {
		return leaders;
	}

	public void setLeaders(int leaders) {
		this.leaders = leaders;
	}

}
