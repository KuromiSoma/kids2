package com.kdc.api.getinvitationcd;

import com.kdc.common.entity.api.base.ResponseBaseEntity;

public class GetInvitationCdResponseEntity extends ResponseBaseEntity {

	private String invitationCd;
	private String expirationDate;

	/**
	 * @return invitationCd
	 */
	public String getInvitationCd() {
		return invitationCd;
	}

	/**
	 * @param invitationCd
	 *            セットする invitationCd
	 */
	public void setInvitationCd(String invitationCd) {
		this.invitationCd = invitationCd;
	}

	/**
	 * @return expirationDate
	 */
	public String getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate
	 *            セットする expirationDate
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

}
