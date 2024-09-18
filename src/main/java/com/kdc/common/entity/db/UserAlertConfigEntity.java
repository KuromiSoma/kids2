package com.kdc.common.entity.db;

public class UserAlertConfigEntity extends CommonColumnsEntity {

	private String groupid;
	private Integer authlevel;
	private Integer notificationauthlevel;
	private Integer locationdisplayflg;
	private Integer movingdistancedisplayflg;
	private Integer batteryleveldisplayflg;
	private Integer accesstimedisplayflg;
	private Integer receptionstatusdisplayflg;
	private Integer batterylevelalertflg;
	private Integer batterynotification;
	private Integer batterypopup;
	private Integer batteryalarmtime;
	private Integer batteryvibrationtime;
	private Integer batteryreference;
	private Integer connectionalertflg;
	private Integer connectionnotification;
	private Integer connectionpopup;
	private Integer connectionalarmtime;
	private Integer connectionvibrationtime;
	private Integer sosalertflg;
	private Integer sosnotification;
	private Integer sospopup;
	private Integer sosalarmtime;
	private Integer sosvibrationtime;
	
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
	 * @return locationdisplayflg
	 */
	public Integer getLocationdisplayflg() {
		return locationdisplayflg;
	}
	/**
	 * @param locationdisplayflg セットする locationdisplayflg
	 */
	public void setLocationdisplayflg(Integer locationdisplayflg) {
		this.locationdisplayflg = locationdisplayflg;
	}
	/**
	 * @return movingdistancedisplayflg
	 */
	public Integer getMovingdistancedisplayflg() {
		return movingdistancedisplayflg;
	}
	/**
	 * @param movingdistancedisplayflg セットする movingdistancedisplayflg
	 */
	public void setMovingdistancedisplayflg(Integer movingdistancedisplayflg) {
		this.movingdistancedisplayflg = movingdistancedisplayflg;
	}
	/**
	 * @return batteryleveldisplayflg
	 */
	public Integer getBatteryleveldisplayflg() {
		return batteryleveldisplayflg;
	}
	/**
	 * @param batteryleveldisplayflg セットする batteryleveldisplayflg
	 */
	public void setBatteryleveldisplayflg(Integer batteryleveldisplayflg) {
		this.batteryleveldisplayflg = batteryleveldisplayflg;
	}
	/**
	 * @return accesstimedisplayflg
	 */
	public Integer getAccesstimedisplayflg() {
		return accesstimedisplayflg;
	}
	/**
	 * @param accesstimedisplayflg セットする accesstimedisplayflg
	 */
	public void setAccesstimedisplayflg(Integer accesstimedisplayflg) {
		this.accesstimedisplayflg = accesstimedisplayflg;
	}
	/**
	 * @return receptionstatusdisplayflg
	 */
	public Integer getReceptionstatusdisplayflg() {
		return receptionstatusdisplayflg;
	}
	/**
	 * @param receptionstatusdisplayflg セットする receptionstatusdisplayflg
	 */
	public void setReceptionstatusdisplayflg(Integer receptionstatusdisplayflg) {
		this.receptionstatusdisplayflg = receptionstatusdisplayflg;
	}
	/**
	 * @return batterylevelalertflg
	 */
	public Integer getBatterylevelalertflg() {
		return batterylevelalertflg;
	}
	/**
	 * @param batterylevelalertflg セットする batterylevelalertflg
	 */
	public void setBatterylevelalertflg(Integer batterylevelalertflg) {
		this.batterylevelalertflg = batterylevelalertflg;
	}
	/**
	 * @return batterynotification
	 */
	public Integer getBatterynotification() {
		return batterynotification;
	}
	/**
	 * @param batterynotification セットする batterynotification
	 */
	public void setBatterynotification(Integer batterynotification) {
		this.batterynotification = batterynotification;
	}
	/**
	 * @return batterypopup
	 */
	public Integer getBatterypopup() {
		return batterypopup;
	}
	/**
	 * @param batterypopup セットする batterypopup
	 */
	public void setBatterypopup(Integer batterypopup) {
		this.batterypopup = batterypopup;
	}
	/**
	 * @return batteryalarmtime
	 */
	public Integer getBatteryalarmtime() {
		return batteryalarmtime;
	}
	/**
	 * @param batteryalarmtime セットする batteryalarmtime
	 */
	public void setBatteryalarmtime(Integer batteryalarmtime) {
		this.batteryalarmtime = batteryalarmtime;
	}
	/**
	 * @return batteryvibrationtime
	 */
	public Integer getBatteryvibrationtime() {
		return batteryvibrationtime;
	}
	/**
	 * @param batteryvibrationtime セットする batteryvibrationtime
	 */
	public void setBatteryvibrationtime(Integer batteryvibrationtime) {
		this.batteryvibrationtime = batteryvibrationtime;
	}
	/**
	 * @return batteryreference
	 */
	public Integer getBatteryreference() {
		return batteryreference;
	}
	/**
	 * @param batteryreference セットする batteryreference
	 */
	public void setBatteryreference(Integer batteryreference) {
		this.batteryreference = batteryreference;
	}
	/**
	 * @return connectionalertflg
	 */
	public Integer getConnectionalertflg() {
		return connectionalertflg;
	}
	/**
	 * @param connectionalertflg セットする connectionalertflg
	 */
	public void setConnectionalertflg(Integer connectionalertflg) {
		this.connectionalertflg = connectionalertflg;
	}
	/**
	 * @return connectionnotification
	 */
	public Integer getConnectionnotification() {
		return connectionnotification;
	}
	/**
	 * @param connectionnotification セットする connectionnotification
	 */
	public void setConnectionnotification(Integer connectionnotification) {
		this.connectionnotification = connectionnotification;
	}
	/**
	 * @return connectionpopup
	 */
	public Integer getConnectionpopup() {
		return connectionpopup;
	}
	/**
	 * @param connectionpopup セットする connectionpopup
	 */
	public void setConnectionpopup(Integer connectionpopup) {
		this.connectionpopup = connectionpopup;
	}
	/**
	 * @return connectionalarmtime
	 */
	public Integer getConnectionalarmtime() {
		return connectionalarmtime;
	}
	/**
	 * @param connectionalarmtime セットする connectionalarmtime
	 */
	public void setConnectionalarmtime(Integer connectionalarmtime) {
		this.connectionalarmtime = connectionalarmtime;
	}
	/**
	 * @return connectionvibrationtime
	 */
	public Integer getConnectionvibrationtime() {
		return connectionvibrationtime;
	}
	/**
	 * @param connectionvibrationtime セットする connectionvibrationtime
	 */
	public void setConnectionvibrationtime(Integer connectionvibrationtime) {
		this.connectionvibrationtime = connectionvibrationtime;
	}
	/**
	 * @return sosalertflg
	 */
	public Integer getSosalertflg() {
		return sosalertflg;
	}
	/**
	 * @param sosalertflg セットする sosalertflg
	 */
	public void setSosalertflg(Integer sosalertflg) {
		this.sosalertflg = sosalertflg;
	}
	/**
	 * @return sosnotification
	 */
	public Integer getSosnotification() {
		return sosnotification;
	}
	/**
	 * @param sosnotification セットする sosnotification
	 */
	public void setSosnotification(Integer sosnotification) {
		this.sosnotification = sosnotification;
	}
	/**
	 * @return sospopup
	 */
	public Integer getSospopup() {
		return sospopup;
	}
	/**
	 * @param sospopup セットする sospopup
	 */
	public void setSospopup(Integer sospopup) {
		this.sospopup = sospopup;
	}
	/**
	 * @return sosalarmtime
	 */
	public Integer getSosalarmtime() {
		return sosalarmtime;
	}
	/**
	 * @param sosalarmtime セットする sosalarmtime
	 */
	public void setSosalarmtime(Integer sosalarmtime) {
		this.sosalarmtime = sosalarmtime;
	}
	/**
	 * @return sosvibrationtime
	 */
	public Integer getSosvibrationtime() {
		return sosvibrationtime;
	}
	/**
	 * @param sosvibrationtime セットする sosvibrationtime
	 */
	public void setSosvibrationtime(Integer sosvibrationtime) {
		this.sosvibrationtime = sosvibrationtime;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
}
