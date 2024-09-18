package com.kdc.common.entity.api.base;

public class RequestBaseEntity {

	private UserInfoEntity userInfo;
	private DeviceInfoEntity deviceInfo;
	private int forceLogin;

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
	 * @return deviceInfo
	 */
	public DeviceInfoEntity getDeviceInfo() {
		return deviceInfo;
	}

	/**
	 * @param deviceInfo
	 *            セットする deviceInfo
	 */
	public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	/**
	 * @return forceLogin
	 */
	public int getForceLogin() {
		return forceLogin;
	}

	/**
	 * @param forceLogin
	 *            セットする forceLogin
	 */
	public void setForceLogin(int forceLogin) {
		this.forceLogin = forceLogin;
	}

}
