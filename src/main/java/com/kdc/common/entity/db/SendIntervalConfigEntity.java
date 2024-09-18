package com.kdc.common.entity.db;

import java.sql.Time;

public class SendIntervalConfigEntity extends CommonColumnsEntity {

	private String groupid;
	private Integer authlevel;
	private Integer dayofweek;
	private Integer configno;
	private Integer usingflag;
	private Time starttime;
	private Time endtime;
	private Integer transmissioninterval;

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
	 * @return dayofweek
	 */
	public Integer getDayofweek() {
		return dayofweek;
	}
	/**
	 * @param dayofweek セットする dayofweek
	 */
	public void setDayofweek(Integer dayofweek) {
		this.dayofweek = dayofweek;
	}
	/**
	 * @return configno
	 */
	public Integer getConfigno() {
		return configno;
	}
	/**
	 * @param configno セットする configno
	 */
	public void setConfigno(Integer configno) {
		this.configno = configno;
	}
	/**
	 * @return usingflag
	 */
	public Integer getUsingflag() {
		return usingflag;
	}
	/**
	 * @param usingflag セットする usingflag
	 */
	public void setUsingflag(Integer usingflag) {
		this.usingflag = usingflag;
	}
	/**
	 * @return starttime
	 */
	public Time getStarttime() {
		return starttime;
	}
	/**
	 * @param starttime セットする starttime
	 */
	public void setStarttime(Time starttime) {
		this.starttime = starttime;
	}
	/**
	 * @return endtime
	 */
	public Time getEndtime() {
		return endtime;
	}
	/**
	 * @param endtime セットする endtime
	 */
	public void setEndtime(Time endtime) {
		this.endtime = endtime;
	}
	/**
	 * @return transmissioninterval
	 */
	public Integer getTransmissioninterval() {
		return transmissioninterval;
	}
	/**
	 * @param transmissioninterval セットする transmissioninterval
	 */
	public void setTransmissioninterval(Integer transmissioninterval) {
		this.transmissioninterval = transmissioninterval;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
}
