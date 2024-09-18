package com.kdc.api.getconfig;

import java.util.List;

import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.entity.api.PlaceNotificationConfigInfoEntity;
import com.kdc.common.entity.api.TransmissionConfigInfoEntity;
import com.kdc.common.entity.api.UserNotificationConfigInfoEntity;
import com.kdc.common.entity.api.UtilityConfigInfoEntity;
import com.kdc.common.entity.api.base.ResponseBaseEntity;

public class GetConfigResponseEntity extends ResponseBaseEntity {

	private UserInfoEntity userInfo;
	private List<UserNotificationConfigInfoEntity> userNotificationConfigInfo;
	private List<PlaceNotificationConfigInfoEntity> placeNotificationConfigInfo;
	private List<TransmissionConfigInfoEntity> transmissionConfigInfo;
	private List<UtilityConfigInfoEntity> utilityConfigInfo;

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
	 * @return userNotificationConfigInfo
	 */
	public List<UserNotificationConfigInfoEntity> getUserNotificationConfigInfo() {
		return userNotificationConfigInfo;
	}

	/**
	 * @param userNotificationConfigInfo
	 *            セットする userNotificationConfigInfo
	 */
	public void setUserNotificationConfigInfo(List<UserNotificationConfigInfoEntity> userNotificationConfigInfo) {
		this.userNotificationConfigInfo = userNotificationConfigInfo;
	}

	/**
	 * @return placeNotificationConfigInfo
	 */
	public List<PlaceNotificationConfigInfoEntity> getPlaceNotificationConfigInfo() {
		return placeNotificationConfigInfo;
	}

	/**
	 * @param placeNotificationConfigInfo
	 *            セットする placeNotificationConfigInfo
	 */
	public void setPlaceNotificationConfigInfo(List<PlaceNotificationConfigInfoEntity> placeNotificationConfigInfo) {
		this.placeNotificationConfigInfo = placeNotificationConfigInfo;
	}

	/**
	 * @return transmissionConfigInfo
	 */
	public List<TransmissionConfigInfoEntity> getTransmissionConfigInfo() {
		return transmissionConfigInfo;
	}

	/**
	 * @param transmissionConfigInfo
	 *            セットする transmissionConfigInfo
	 */
	public void setTransmissionConfigInfo(List<TransmissionConfigInfoEntity> transmissionConfigInfo) {
		this.transmissionConfigInfo = transmissionConfigInfo;
	}

	/**
	 * @return utilityConfigInfo
	 */
	public List<UtilityConfigInfoEntity> getUtilityConfigInfo() {
		return utilityConfigInfo;
	}

	/**
	 * @param utilityConfigInfo
	 *            セットする utilityConfigInfo
	 */
	public void setUtilityConfigInfo(List<UtilityConfigInfoEntity> utilityConfigInfo) {
		this.utilityConfigInfo = utilityConfigInfo;
	}

}
