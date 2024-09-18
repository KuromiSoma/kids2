package com.kdc.api.registernotification;

import com.kdc.common.entity.api.base.RequestBaseEntity;
import com.kdc.common.entity.db.NotificationInfoEntity;

/**
 * グループ情報エンティティ
 * @author ケリム
 *
 */
public class RegisterNotificationRequestEntity extends RequestBaseEntity{
	
	private Integer mode;
	private NotificationInfoEntity notificationInfo;
	
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
	 * @return notificationInfo
	 */
	public NotificationInfoEntity getNotificationInfo() {
		return notificationInfo;
	}
	/**
	 * @param notificationInfo セットする notificationInfo
	 */
	public void setNotificationInfo(NotificationInfoEntity notificationInfo) {
		this.notificationInfo = notificationInfo;
		
	}
}
