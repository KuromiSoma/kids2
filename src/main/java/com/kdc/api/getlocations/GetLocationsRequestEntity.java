package com.kdc.api.getlocations;

import com.kdc.common.entity.api.base.RequestBaseEntity;

public class GetLocationsRequestEntity extends RequestBaseEntity {

	private String targetDate;

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
