package com.kdc.api.getlocations;

import java.util.List;

import com.kdc.common.entity.api.UserLocationInfoEntity;
import com.kdc.common.entity.api.base.ResponseBaseEntity;

public class GetLocationsResponseEntity extends ResponseBaseEntity {

	private List<UserLocationInfoEntity> userLocationInfo;

	/**
	 * @return userLocationInfo
	 */
	public List<UserLocationInfoEntity> getUserLocationInfo() {
		return userLocationInfo;
	}

	/**
	 * @param userLocationInfo
	 *            セットする userLocationInfo
	 */
	public void setUserLocationInfo(List<UserLocationInfoEntity> userLocationInfo) {
		this.userLocationInfo = userLocationInfo;
	}

}
