package com.kdc.mybatis.domain.api.getlocations;

import java.sql.Timestamp;

public class UserInfoDomain {

	// user_location ユーザ位置
	private String latitude;
	private String longitude;
	private String movingdistance;
	private String batterylevel;
	private String receptionstatus;
	private String connectionstatus;
	private Timestamp lastlocationdate;
	
	// user_master ユーザ情報
	private String userid;
	private String password;
	private String username;
	private String iconid;
	private String groupid;
	private String authlevel;
	private String linecolor;
	private String markercolor;
	
	// user_notification ユーザ通知
	private String locationdisplayflg;
	private String movingdistancedisplayflg;
	private String batteryleveldisplayflg;
	private String accesstimedisplayflg;
	private String receptionstatusdisplayflg;
	
	// user_location_record ユーザ位置履歴
	private String recordlatitude;
	private String recordlongitude;
	private String recordbatterylevel;
	private String recordreceptionstatus;
	private String recordreceivedate;
	
	/**
	 * @return latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude セットする latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude セットする longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return movingdistance
	 */
	public String getMovingdistance() {
		return movingdistance;
	}
	/**
	 * @param movingdistance セットする movingdistance
	 */
	public void setMovingdistance(String movingdistance) {
		this.movingdistance = movingdistance;
	}
	/**
	 * @return batterylevel
	 */
	public String getBatterylevel() {
		return batterylevel;
	}
	/**
	 * @param batterylevel セットする batterylevel
	 */
	public void setBatterylevel(String batterylevel) {
		this.batterylevel = batterylevel;
	}
	/**
	 * @return receptionstatus
	 */
	public String getReceptionstatus() {
		return receptionstatus;
	}
	/**
	 * @param receptionstatus セットする receptionstatus
	 */
	public void setReceptionstatus(String receptionstatus) {
		this.receptionstatus = receptionstatus;
	}
	/**
	 * @return connectionstatus
	 */
	public String getConnectionstatus() {
		return connectionstatus;
	}
	/**
	 * @param connectionstatus セットする connectionstatus
	 */
	public void setConnectionstatus(String connectionstatus) {
		this.connectionstatus = connectionstatus;
	}
	/**
	 * @return lastlocationdate
	 */
	public Timestamp getLastlocationdate() {
		return lastlocationdate;
	}
	/**
	 * @param lastlocationdate セットする lastlocationdate
	 */
	public void setLastlocationdate(Timestamp lastlocationdate) {
		this.lastlocationdate = lastlocationdate;
	}
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
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password セットする password
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return iconid
	 */
	public String getIconid() {
		return iconid;
	}
	/**
	 * @param iconid セットする iconid
	 */
	public void setIconid(String iconid) {
		this.iconid = iconid;
	}
	/**
	 * @return groupid
	 */
	public String getGroupid() {
		return groupid;
	}
	/**
	 * @param groupid セットする groupid
	 */
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	/**
	 * @return authlevel
	 */
	public String getAuthlevel() {
		return authlevel;
	}
	/**
	 * @param authlevel セットする authlevel
	 */
	public void setAuthlevel(String authlevel) {
		this.authlevel = authlevel;
	}
	/**
	 * @return linecolor
	 */
	public String getLinecolor() {
		return linecolor;
	}
	/**
	 * @param linecolor セットする linecolor
	 */
	public void setLinecolor(String linecolor) {
		this.linecolor = linecolor;
	}
	/**
	 * @return markercolor
	 */
	public String getMarkercolor() {
		return markercolor;
	}
	/**
	 * @param markercolor セットする markercolor
	 */
	public void setMarkercolor(String markercolor) {
		this.markercolor = markercolor;
	}
	/**
	 * @return locationdisplayflg
	 */
	public String getLocationdisplayflg() {
		return locationdisplayflg;
	}
	/**
	 * @param locationdisplayflg セットする locationdisplayflg
	 */
	public void setLocationdisplayflg(String locationdisplayflg) {
		this.locationdisplayflg = locationdisplayflg;
	}
	/**
	 * @return movingdistancedisplayflg
	 */
	public String getMovingdistancedisplayflg() {
		return movingdistancedisplayflg;
	}
	/**
	 * @param movingdistancedisplayflg セットする movingdistancedisplayflg
	 */
	public void setMovingdistancedisplayflg(String movingdistancedisplayflg) {
		this.movingdistancedisplayflg = movingdistancedisplayflg;
	}
	/**
	 * @return batteryleveldisplayflg
	 */
	public String getBatteryleveldisplayflg() {
		return batteryleveldisplayflg;
	}
	/**
	 * @param batteryleveldisplayflg セットする batteryleveldisplayflg
	 */
	public void setBatteryleveldisplayflg(String batteryleveldisplayflg) {
		this.batteryleveldisplayflg = batteryleveldisplayflg;
	}
	/**
	 * @return accesstimedisplayflg
	 */
	public String getAccesstimedisplayflg() {
		return accesstimedisplayflg;
	}
	/**
	 * @param accesstimedisplayflg セットする accesstimedisplayflg
	 */
	public void setAccesstimedisplayflg(String accesstimedisplayflg) {
		this.accesstimedisplayflg = accesstimedisplayflg;
	}
	/**
	 * @return receptionstatusdisplayflg
	 */
	public String getReceptionstatusdisplayflg() {
		return receptionstatusdisplayflg;
	}
	/**
	 * @param receptionstatusdisplayflg セットする receptionstatusdisplayflg
	 */
	public void setReceptionstatusdisplayflg(String receptionstatusdisplayflg) {
		this.receptionstatusdisplayflg = receptionstatusdisplayflg;
	}
	/**
	 * @return recordlatitude
	 */
	public String getRecordlatitude() {
		return recordlatitude;
	}
	/**
	 * @param recordlatitude セットする recordlatitude
	 */
	public void setRecordlatitude(String recordlatitude) {
		this.recordlatitude = recordlatitude;
	}
	/**
	 * @return recordlongitude
	 */
	public String getRecordlongitude() {
		return recordlongitude;
	}
	/**
	 * @param recordlongitude セットする recordlongitude
	 */
	public void setRecordlongitude(String recordlongitude) {
		this.recordlongitude = recordlongitude;
	}
	/**
	 * @return recordbatterylevel
	 */
	public String getRecordbatterylevel() {
		return recordbatterylevel;
	}
	/**
	 * @param recordbatterylevel セットする recordbatterylevel
	 */
	public void setRecordbatterylevel(String recordbatterylevel) {
		this.recordbatterylevel = recordbatterylevel;
	}
	/**
	 * @return recordreceptionstatus
	 */
	public String getRecordreceptionstatus() {
		return recordreceptionstatus;
	}
	/**
	 * @param recordreceptionstatus セットする recordreceptionstatus
	 */
	public void setRecordreceptionstatus(String recordreceptionstatus) {
		this.recordreceptionstatus = recordreceptionstatus;
	}
	/**
	 * @return recordreceivedate
	 */
	public String getRecordreceivedate() {
		return recordreceivedate;
	}
	/**
	 * @param recordreceivedate セットする recordreceivedate
	 */
	public void setRecordreceivedate(String recordreceivedate) {
		this.recordreceivedate = recordreceivedate;
	}
	
}

