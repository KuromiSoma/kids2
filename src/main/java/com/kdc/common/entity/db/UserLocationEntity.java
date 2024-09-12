package com.kdc.common.entity.db;

import java.sql.Timestamp;

public class UserLocationEntity extends CommonColumnsEntity {

	private String userid;
	private String longitudeandlatitude;
	private Integer movingdistance;
	private Integer batterylevel;
	private Integer receptionstatus;
	private Integer connectionstatus;
	private Timestamp lastlocationdate;
	
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
	 * @return movingdistance
	 */
	public Integer getMovingdistance() {
		return movingdistance;
	}
	/**
	 * @param movingdistance セットする movingdistance
	 */
	public void setMovingdistance(Integer movingdistance) {
		this.movingdistance = movingdistance;
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
	/**
	 * @return connectionstatus
	 */
	public Integer getConnectionstatus() {
		return connectionstatus;
	}
	/**
	 * @param connectionstatus セットする connectionstatus
	 */
	public void setConnectionstatus(Integer connectionstatus) {
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
	
}