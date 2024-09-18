package com.kdc.api.registeruser;

import com.kdc.common.entity.api.base.DeviceInfoEntity;

public class RegisterUserRequestEntity {

	private Integer mode;
	private String userId;
	private String password;
	private String userName;
	private String invitationCd;
	private DeviceInfoEntity deviceInfo;

	/**
	 * @return mode
	 */
	public Integer getMode() {
		return mode;
	}

	/**
	 * @param mode セットする mode
	 */
	public void setMode(Integer mode) {
		this.mode = mode;
	}

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            セットする userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            セットする password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            セットする userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

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

}
