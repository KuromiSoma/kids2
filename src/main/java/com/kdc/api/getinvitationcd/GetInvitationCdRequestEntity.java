package com.kdc.api.getinvitationcd;

import com.kdc.common.entity.api.base.RequestBaseEntity;

public class GetInvitationCdRequestEntity extends RequestBaseEntity {

	private String authLevel;

	/**
	 * @return authLevel
	 */
	public String getAuthLevel() {
		return authLevel;
	}

	/**
	 * @param authLevel
	 *            セットする authLevel
	 */
	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;
	}

}
