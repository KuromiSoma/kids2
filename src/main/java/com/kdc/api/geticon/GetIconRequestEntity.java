package com.kdc.api.geticon;

import com.kdc.common.entity.api.base.RequestBaseEntity;

public class GetIconRequestEntity extends RequestBaseEntity {

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
