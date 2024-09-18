package com.kdc.common.entity.db;

import java.sql.Timestamp;

public class UserAlertRecordEntity extends CommonColumnsEntity {

	private String userid;
	private Timestamp lastbatteryalertdate;
	private Timestamp lastdisconnectionalertdate;
	private Timestamp lastreconnectionalertdate;
	private Timestamp lastsosalertdate;
	
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
	 * @return lastbatteryalertdate
	 */
	public Timestamp getLastbatteryalertdate() {
		return lastbatteryalertdate;
	}
	/**
	 * @param lastbatteryalertdate セットする lastbatteryalertdate
	 */
	public void setLastbatteryalertdate(Timestamp lastbatteryalertdate) {
		this.lastbatteryalertdate = lastbatteryalertdate;
	}
	/**
	 * @return lastdisconnectionalertdate
	 */
	public Timestamp getLastdisconnectionalertdate() {
		return lastdisconnectionalertdate;
	}
	/**
	 * @param lastdisconnectionalertdate セットする lastdisconnectionalertdate
	 */
	public void setLastdisconnectionalertdate(Timestamp lastdisconnectionalertdate) {
		this.lastdisconnectionalertdate = lastdisconnectionalertdate;
	}
	/**
	 * @return lastreconnectionalertdate
	 */
	public Timestamp getLastreconnectionalertdate() {
		return lastreconnectionalertdate;
	}
	/**
	 * @param lastreconnectionalertdate セットする lastreconnectionalertdate
	 */
	public void setLastreconnectionalertdate(Timestamp lastreconnectionalertdate) {
		this.lastreconnectionalertdate = lastreconnectionalertdate;
	}
	/**
	 * @return lastsosalertdate
	 */
	public Timestamp getLastsosalertdate() {
		return lastsosalertdate;
	}
	/**
	 * @param lastsosalertdate セットする lastsosalertdate
	 */
	public void setLastsosalertdate(Timestamp lastsosalertdate) {
		this.lastsosalertdate = lastsosalertdate;
	}
	
}
