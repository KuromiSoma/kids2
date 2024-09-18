package com.kdc.common.entity.db;

import java.sql.Timestamp;

public class UserDeviceEntity extends CommonColumnsEntity {

	private String userid;
	private String telephonenumber;
	private String deviceid;
	private String tokenid;
	private Timestamp configsyncdate;
	
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
	 * @return telephonenumber
	 */
	public String getTelephonenumber() {
		return telephonenumber;
	}
	/**
	 * @param telephonenumber セットする telephonenumber
	 */
	public void setTelephonenumber(String telephonenumber) {
		this.telephonenumber = telephonenumber;
	}
	/**
	 * @return deviceid
	 */
	public String getDeviceid() {
		return deviceid;
	}
	/**
	 * @param deviceid セットする deviceid
	 */
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	/**
	 * @return tokenid
	 */
	public String getTokenid() {
		return tokenid;
	}
	/**
	 * @param tokenid セットする tokenid
	 */
	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}
	/**
	 * @return configsyncdate
	 */
	public Timestamp getConfigsyncdate() {
		return configsyncdate;
	}
	/**
	 * @param configsyncdate セットする configsyncdate
	 */
	public void setConfigsyncdate(Timestamp configsyncdate) {
		this.configsyncdate = configsyncdate;
	}
	
}
