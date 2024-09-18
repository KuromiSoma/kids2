package com.kdc.api.getnotifications;

import java.util.List;

import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.db.NotificationInfoEntity;

public class GetNotificationsResponseEntity extends ResponseBaseEntity{

	private List<NotificationInfoEntity> notificationInfo;

	/**
	 * @return notificationInfo
	 */
	public List<NotificationInfoEntity> getNotificationInfo() {
		return notificationInfo;
	}

	/**
	 * @param notificationInfo セットする notificationInfo
	 */
	public void setNotificationInfo(List<NotificationInfoEntity> notificationInfo) {
		this.notificationInfo = notificationInfo;
	}
	
	
}
