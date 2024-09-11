package com.kdc.common.entity.api.base;

public class DeviceInfoCoreEntity {

	private String userId;
	private String telephoneNumber;
	private String deviceId;
	
	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return telephoneNumber
	 */
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	/**
	 * @param telephoneNumber セットする telephoneNumber
	 */
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	/**
	 * @return deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * @param deviceId セットする deviceId
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}