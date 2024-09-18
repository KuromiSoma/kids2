package com.kdc.api.checkinvitationcd;

import com.kdc.common.entity.api.base.ResponseBaseEntity;

public class CheckInvitationCdResponseEntity extends ResponseBaseEntity {

	private Integer authFlg;

	/**
	 * @return authFlg
	 */
	public Integer getAuthFlg() {
		return authFlg;
	}

	/**
	 * @param authFlg
	 *            セットする authFlg
	 */
	public void setAuthFlg(Integer authFlg) {
		this.authFlg = authFlg;
	}

}
