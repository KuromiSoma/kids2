package com.kdc.api.getrecords;

import com.kdc.common.entity.api.base.RequestBaseEntity;

public class GetRecordsRequestEntity extends RequestBaseEntity {

	private String targetUserId;
	private String targetDate;

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

	/**
	 * @return targetDate
	 */
	public String getTargetDate() {
		return targetDate;
	}

	/**
	 * @param targetDate
	 *            セットする targetDate
	 */
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}

}
