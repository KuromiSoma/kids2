package com.kdc.api.checkinvitationcd;

import com.kdc.common.entity.api.base.DeviceInfoEntity;

public class CheckInvitationCdRequestEntity {

	private Integer mode;
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
