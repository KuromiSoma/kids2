package com.kdc.common.entity.api;

public class PushNotificationEntity {

	private String tokenid;
	private String notification;
	private String popup;
	private String alarmtime;
	private String vibrationtime;

	/**
	 * @return tokenid
	 */
	public String getTokenid() {
		return tokenid;
	}

	/**
	 * @param tokenid
	 *            セットする tokenid
	 */
	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}

	/**
	 * @return notification
	 */
	public String getNotification() {
		return notification;
	}

	/**
	 * @param notification
	 *            セットする notification
	 */
	public void setNotification(String notification) {
		this.notification = notification;
	}

	/**
	 * @return popup
	 */
	public String getPopup() {
		return popup;
	}

	/**
	 * @param popup
	 *            セットする popup
	 */
	public void setPopup(String popup) {
		this.popup = popup;
	}

	/**
	 * @return alarmtime
	 */
	public String getAlarmtime() {
		return alarmtime;
	}

	/**
	 * @param alarmtime
	 *            セットする alarmtime
	 */
	public void setAlarmtime(String alarmtime) {
		this.alarmtime = alarmtime;
	}

	/**
	 * @return vibrationtime
	 */
	public String getVibrationtime() {
		return vibrationtime;
	}

	/**
	 * @param vibrationtime
	 *            セットする vibrationtime
	 */
	public void setVibrationtime(String vibrationtime) {
		this.vibrationtime = vibrationtime;
	}

}
