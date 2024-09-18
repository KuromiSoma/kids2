package com.kdc.common.entity.api;

import java.util.List;

import com.kdc.common.entity.api.base.UserInfoEntity;

public class UserLocationInfoEntity {

	private UserInfoEntity userInfo;
	private LocationInfoEntity locationInfo;
	private List<LocationRecordInfoEntity> locationRecordInfo;

	/**
	 * @return userInfo
	 */
	public UserInfoEntity getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo
	 *            セットする userInfo
	 */
	public void setUserInfo(UserInfoEntity userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return locationInfo
	 */
	public LocationInfoEntity getLocationInfo() {
		return locationInfo;
	}

	/**
	 * @param locationInfo
	 *            セットする locationInfo
	 */
	public void setLocationInfo(LocationInfoEntity locationInfo) {
		this.locationInfo = locationInfo;
	}

	/**
	 * @return locationRecordInfo
	 */
	public List<LocationRecordInfoEntity> getLocationRecordInfo() {
		return locationRecordInfo;
	}

	/**
	 * @param locationRecordInfo
	 *            セットする locationRecordInfo
	 */
	public void setLocationRecordInfo(List<LocationRecordInfoEntity> locationRecordInfo) {
		this.locationRecordInfo = locationRecordInfo;
	}

}
