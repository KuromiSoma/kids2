package com.kdc.api.registergroup;

import com.kdc.common.entity.api.base.ResponseBaseEntity;

public class RegisterGroupResponseEntity extends ResponseBaseEntity {

	private String groupId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
}
