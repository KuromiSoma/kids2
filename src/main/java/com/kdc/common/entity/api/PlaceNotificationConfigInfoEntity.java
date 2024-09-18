package com.kdc.common.entity.api;

import java.util.List;

public class PlaceNotificationConfigInfoEntity {

	private String authLevel;
	private String notificationAuthLevel;
	private List<PlaceNotificationInfoEntity> placeNotificationInfo;

	/**
	 * @return authLevel
	 */
	public String getAuthLevel() {
		return authLevel;
	}

	/**
	 * @param authLevel
	 *            セットする authLevel
	 */
	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;
	}

	/**
	 * @return notificationAuthLevel
	 */
	public String getNotificationAuthLevel() {
		return notificationAuthLevel;
	}

	/**
	 * @param notificationAuthLevel
	 *            セットする notificationAuthLevel
	 */
	public void setNotificationAuthLevel(String notificationAuthLevel) {
		this.notificationAuthLevel = notificationAuthLevel;
	}

	/**
	 * @return placeNotificationInfo
	 */
	public List<PlaceNotificationInfoEntity> getPlaceNotificationInfo() {
		return placeNotificationInfo;
	}

	/**
	 * @param placeNotificationInfo
	 *            セットする placeNotificationInfo
	 */
	public void setPlaceNotificationInfo(List<PlaceNotificationInfoEntity> placeNotificationInfo) {
		this.placeNotificationInfo = placeNotificationInfo;
	}

}
