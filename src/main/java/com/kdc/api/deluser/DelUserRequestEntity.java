package com.kdc.api.deluser;

import com.kdc.common.entity.api.base.RequestBaseEntity;

public class DelUserRequestEntity extends RequestBaseEntity {

	private String targetUserId;

	/**
	 * @return targetUserId
	 */
	public String getTargetUserId() {
		return targetUserId;
	}

	/**
	 * @param targetUserId
	 *            セットする targetUserId
	 */
	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}

}