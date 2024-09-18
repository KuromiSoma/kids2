package com.kdc.common.entity;


public class KdcAlertEntity {

	private String userid;
	private String username;
	private String placeid;
	private Integer authlevel;
	private Integer notificationauthlevel;
	
	/**
	 * @return userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid セットする userid
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username セットする username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return placeid
	 */
	public String getPlaceid() {
		return placeid;
	}
	/**
	 * @param placeid セットする placeid
	 */
	public void setPlaceid(String placeid) {
		this.placeid = placeid;
	}
	/**
	 * @return authlevel
	 */
	public Integer getAuthlevel() {
		return authlevel;
	}
	/**
	 * @param authlevel セットする authlevel
	 */
	public void setAuthlevel(Integer authlevel) {
		this.authlevel = authlevel;
	}
	/**
	 * @return notificationauthlevel
	 */
	public Integer getNotificationauthlevel() {
		return notificationauthlevel;
	}
	/**
	 * @param notificationauthlevel セットする notificationauthlevel
	 */
	public void setNotificationauthlevel(Integer notificationauthlevel) {
		this.notificationauthlevel = notificationauthlevel;
	}
	
}
