package com.kdc.common.entity.api;

public class TransmissionConfigInfoEntity {

	private String authLevel;
	private String dayOfWeek;
	private String configNo;
	private String startTime;
	private String endTime;
	private String interval;

	/**
	 * @return authLevel
	 */
	public String getAuthLevel() {
		return authLevel;
	}

	/**
	 * @param authLevel
	 *            セットする authLevel
	 */
	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;
	}

	/**
	 * @return dayOfWeek
	 */
	public String getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @param dayOfWeek
	 *            セットする dayOfWeek
	 */
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/**
	 * @return configNo
	 */
	public String getConfigNo() {
		return configNo;
	}

	/**
	 * @param configNo セットする configNo
	 */
	public void setConfigNo(String configNo) {
		this.configNo = configNo;
	}

	/**
	 * @return startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            セットする startTime
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            セットする endTime
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return interval
	 */
	public String getInterval() {
		return interval;
	}

	/**
	 * @param interval
	 *            セットする interval
	 */
	public void setInterval(String interval) {
		this.interval = interval;
	}

}
