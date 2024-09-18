package com.kdc.common.entity.db;

public class PlaceAlertConfigEntity extends CommonColumnsEntity {

	private String groupid;
	private Integer authlevel;
	private Integer notificationauthlevel;
	private String placeid;
	private Integer placealertflg;
	private Integer placenotification;
	private Integer placepopup;
	private Integer placealarmtime;
	private Integer placevibrationtime;

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
	 * @return placealertflg
	 */
	public Integer getPlacealertflg() {
		return placealertflg;
	}
	/**
	 * @param placealertflg セットする placealertflg
	 */
	public void setPlacealertflg(Integer placealertflg) {
		this.placealertflg = placealertflg;
	}
	/**
	 * @return placenotification
	 */
	public Integer getPlacenotification() {
		return placenotification;
	}
	/**
	 * @param placenotification セットする placenotification
	 */
	public void setPlacenotification(Integer placenotification) {
		this.placenotification = placenotification;
	}
	/**
	 * @return placepopup
	 */
	public Integer getPlacepopup() {
		return placepopup;
	}
	/**
	 * @param placepopup セットする placepopup
	 */
	public void setPlacepopup(Integer placepopup) {
		this.placepopup = placepopup;
	}
	/**
	 * @return placealarmtime
	 */
	public Integer getPlacealarmtime() {
		return placealarmtime;
	}
	/**
	 * @param placealarmtime セットする placealarmtime
	 */
	public void setPlacealarmtime(Integer placealarmtime) {
		this.placealarmtime = placealarmtime;
	}
	/**
	 * @return placevibrationtime
	 */
	public Integer getPlacevibrationtime() {
		return placevibrationtime;
	}
	/**
	 * @param placevibrationtime セットする placevibrationtime
	 */
	public void setPlacevibrationtime(Integer placevibrationtime) {
		this.placevibrationtime = placevibrationtime;
	}

	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
}
