package com.kdc.common.entity.db;

public class NotificationInfoEntity extends CommonColumnsEntity{

	//グループID
	private String groupId;
	//ユーザーID
	private String userId;
	//通知日
	private String notificationDate;
	//通知時間
	private String notificationTime;
	//管理レベル
	private Integer authLevel;
	//通知先管理レベル
	private Integer notificationLevel;
	//通知フラグ
	private Integer notificationFlg;
	//メッセージ
	private String message;
	/**
	 * @return groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId セットする groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
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
	 * @return notificationDate
	 */
	public String getNotificationDate() {
		return notificationDate;
	}
	/**
	 * @param notificationDate セットする notificationDate
	 */
	public void setNotificationDate(String notificationDate) {
		this.notificationDate = notificationDate;
	}
	/**
	 * @return notificationTime
	 */
	public String getNotificationTime() {
		return notificationTime;
	}
	/**
	 * @param notificationTime セットする notificationTime
	 */
	public void setNotificationTime(String notificationTime) {
		this.notificationTime = notificationTime;
	}
	/**
	 * @return authLevel
	 */
	public Integer getAuthLevel() {
		return authLevel;
	}
	/**
	 * @param authLevel セットする authLevel
	 */
	public void setAuthLevel(Integer authLevel) {
		this.authLevel = authLevel;
	}
	/**
	 * @return notificationLevel
	 */
	public Integer getNotificationLevel() {
		return notificationLevel;
	}
	/**
	 * @param notificationLevel セットする notificationLevel
	 */
	public void setNotificationLevel(Integer notificationLevel) {
		this.notificationLevel = notificationLevel;
	}
	/**
	 * @return notificationFlg
	 */
	public Integer getNotificationFlg() {
		return notificationFlg;
	}
	/**
	 * @param notificationFlg セットする notificationFlg
	 */
	public void setNotificationFlg(Integer notificationFlg) {
		this.notificationFlg = notificationFlg;
	}
	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message セットする message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
	
	
