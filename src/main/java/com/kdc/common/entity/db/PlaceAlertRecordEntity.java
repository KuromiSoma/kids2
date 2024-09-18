package com.kdc.common.entity.db;

import java.sql.Timestamp;

public class PlaceAlertRecordEntity extends CommonColumnsEntity {

	private String userid;
	private String placeid;
	private Timestamp lastplaceinalertdate;
	private Timestamp lastplaceoutalertdate;
	
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
	 * @return lastplaceinalertdate
	 */
	public Timestamp getLastplaceinalertdate() {
		return lastplaceinalertdate;
	}
	/**
	 * @param lastplaceinalertdate セットする lastplaceinalertdate
	 */
	public void setLastplaceinalertdate(Timestamp lastplaceinalertdate) {
		this.lastplaceinalertdate = lastplaceinalertdate;
	}
	/**
	 * @return lastplaceoutalertdate
	 */
	public Timestamp getLastplaceoutalertdate() {
		return lastplaceoutalertdate;
	}
	/**
	 * @param lastplaceoutalertdate セットする lastplaceoutalertdate
	 */
	public void setLastplaceoutalertdate(Timestamp lastplaceoutalertdate) {
		this.lastplaceoutalertdate = lastplaceoutalertdate;
	}

}
