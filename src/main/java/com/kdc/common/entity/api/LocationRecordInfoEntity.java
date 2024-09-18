package com.kdc.common.entity.api;

public class LocationRecordInfoEntity {

	private String recordDate;
	private String latitude;
	private String longitude;
	private String batteryLevel;
	private String receptionStatus;

	/**
	 * @return recordDate
	 */
	public String getRecordDate() {
		return recordDate;
	}

	/**
	 * @param recordDate
	 *            セットする recordDate
	 */
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	/**
	 * @return latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            セットする latitude
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
	 * @param longitude
	 *            セットする longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return batteryLevel
	 */
	public String getBatteryLevel() {
		return batteryLevel;
	}

	/**
	 * @param batteryLevel
	 *            セットする batteryLevel
	 */
	public void setBatteryLevel(String batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	/**
	 * @return receptionStatus
	 */
	public String getReceptionStatus() {
		return receptionStatus;
	}

	/**
	 * @param receptionStatus
	 *            セットする receptionStatus
	 */
	public void setReceptionStatus(String receptionStatus) {
		this.receptionStatus = receptionStatus;
	}

}
