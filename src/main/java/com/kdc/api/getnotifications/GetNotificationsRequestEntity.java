package com.kdc.api.getnotifications;

import com.kdc.common.entity.api.base.RequestBaseEntity;

public class GetNotificationsRequestEntity extends RequestBaseEntity{
	
	//通知日	
	private String notificationDate;

	/**
	 * @return notificationDate
	 */
	public String getNotificationDate() {
		return notificationDate;
	}

	/**
	 * @param notificationdate セットする notificationDate
	 */
	public void setNotificationdate(String notificationDate) {
		this.notificationDate = notificationDate;
	}

}
