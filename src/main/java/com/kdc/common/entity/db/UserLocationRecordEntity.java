package com.kdc.common.entity.db;

import java.sql.Timestamp;

public class UserLocationRecordEntity extends CommonColumnsEntity {

	private String userid;
	private Timestamp receivedate;
	private String longitudeandlatitude;
	private Integer batterylevel;
	private Integer receptionstatus;
	
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
	 * @return receivedate
	 */
	public Timestamp getReceivedate() {
		return receivedate;
	}
	/**
	 * @param receivedate セットする receivedate
	 */
	public void setReceivedate(Timestamp receivedate) {
		this.receivedate = receivedate;
	}
	/**
	 * @return longitudeandlatitude
	 */
	public String getLongitudeandlatitude() {
		return longitudeandlatitude;
	}
	/**
	 * @param longitudeandlatitude セットする longitudeandlatitude
	 */
	public void setLongitudeandlatitude(String longitudeandlatitude) {
		this.longitudeandlatitude = longitudeandlatitude;
	}
	/**
	 * @return batterylevel
	 */
	public Integer getBatterylevel() {
		return batterylevel;
	}
	/**
	 * @param batterylevel セットする batterylevel
	 */
	public void setBatterylevel(Integer batterylevel) {
		this.batterylevel = batterylevel;
	}
	/**
	 * @return receptionstatus
	 */
	public Integer getReceptionstatus() {
		return receptionstatus;
	}
	/**
	 * @param receptionstatus セットする receptionstatus
	 */
	public void setReceptionstatus(Integer receptionstatus) {
		this.receptionstatus = receptionstatus;
	}
	
}